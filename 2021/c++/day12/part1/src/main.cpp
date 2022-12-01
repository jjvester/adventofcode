#include <ctype.h>
#include <fstream>
#include <iostream>
#include <map>
#include <set>
#include <sstream>
#include <stack>
#include <stdio.h>
#include <string>
#include <tuple>
#include <vector>

struct Cave {
  Cave(const std::string &id, bool isSmall) : id(id), isSmall(isSmall) {}
  const std::string id;
  bool isSmall;
  std::vector<Cave *> *neighbors;

  const bool operator=(const Cave &other) const {
    return other.id.compare(id) == 0;
  }

  const bool operator<(const Cave &other) const {
    return other.id.compare(id) == 0;
  }
};

auto caveSystem = new std::map<std::string, std::vector<Cave *> *>();
auto paths = new std::vector<std::vector<Cave *> *>();

bool illegal(Cave &current, std::vector<Cave *> *breadCrumbs) {
  for (auto cave : *breadCrumbs) {
    if (cave->isSmall && cave->id.compare(current.id) == 0) {
      return true;
    }
  }

  return false;
}

bool exists(const std::string &key) {
  return caveSystem->find(key) != caveSystem->end();
}

bool isLower(const std::string &item) {
  for (unsigned int i = 0; i < item.length(); ++i) {
    if (isupper(item[i])) {
      return false;
    }
  }
  return true;
}

bool isStart(const Cave &candidate) {
  return candidate.id.compare("start") == 0;
}

bool isEnd(const Cave &candidate) { return candidate.id.compare("end") == 0; }

inline const std::tuple<Cave *, Cave *> split(const std::string &src) {
  std::stringstream ss(src);

  std::string token;
  std::getline(ss, token, '-');
  auto origin = new Cave(token, isLower(token));

  std::getline(ss, token, '-');
  auto destination = new Cave(token, isLower(token));

  return std::make_tuple(origin, destination);
}

inline std::ostream &operator<<(std::ostream &out, const Cave &cave) {
  out << cave.id << ":" << (cave.isSmall ? "true" : "false") << "\t";
  return out;
}

void addRoute(const std::tuple<Cave *, Cave *> &tuple) {
  const auto origin = std::get<0>(tuple);
  const auto destination = std::get<1>(tuple);

  if (exists(origin->id)) {
    caveSystem->find(origin->id)->second->push_back(destination);
  } else {
    auto neighbors = new std::vector<Cave *>();
    neighbors->push_back(destination);
    caveSystem->insert(std::make_pair(origin->id, neighbors));
  }

  if (exists(destination->id)) {
    caveSystem->find(destination->id)->second->push_back(origin);
  } else {
    auto neighbors = new std::vector<Cave *>();
    neighbors->push_back(origin);
    caveSystem->insert(std::make_pair(destination->id, neighbors));
  }

  origin->neighbors = caveSystem->find(origin->id)->second;
  destination->neighbors = caveSystem->find(destination->id)->second;
}

void findPaths(Cave &current, std::vector<Cave *> *breadcrumbs) {
  if (isEnd(current)) {
    breadcrumbs->push_back(&current);
    paths->push_back(breadcrumbs);
  } else {
    breadcrumbs->push_back(&current);
    for (auto neighbor : *(current.neighbors)) {
      if (!illegal(*neighbor, breadcrumbs)) {
        auto fork = new std::vector<Cave *>(breadcrumbs->size());
        std::copy(breadcrumbs->begin(), breadcrumbs->end(), fork->begin());
        findPaths(*neighbor, fork);
      }
    }
  }
}

int main() {
  Cave *start;
  Cave *end;

  std::ifstream infile("../resources/input");
  std::string line;
  while (std::getline(infile, line)) {
    const auto route = split(line);

    if (isStart(*std::get<0>(route))) {
      start = std::get<0>(route);
    }

    if (isEnd(*std::get<0>(route))) {
      end = std::get<0>(route);
    }

    addRoute(route);
  }

  findPaths(*start, new std::vector<Cave *>());
  for (auto path : *paths) {
    for (auto cave : *path) {
      std::cout << cave->id << ",";
    }

    std::cout << std::endl;
  }
  std::cout << paths->size() << std::endl;

  // auto it = caveSystem->begin();
  // while (it != caveSystem->end()) {
  //   std::string id = it->first;
  //   auto neighbors = it->second;

  //   std::cout << "Origin " << id << std::endl;
  //   std::cout << "Neighbors ";
  //   for (auto neighbor : *neighbors) {
  //     std::cout << neighbor->id << " ";
  //   }
  //   std::cout << std::endl;
  //   it++;
  // }

  return 0;
}