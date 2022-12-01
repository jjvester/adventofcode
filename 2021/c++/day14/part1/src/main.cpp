#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>

std::string polymerTemplate;
auto elementRules = std::map<const std::string, const std::string>();

bool exists(const std::string &key) {
  return elementRules.find(key) != elementRules.end();
}

inline void parseInsertionPair(const std::string &src) {
  std::stringstream ss(src);
  std::string token;
  std::getline(ss, token, ' ');
  const auto rule = token;

  std::getline(ss, token, '-');
  std::getline(ss, token, '>');
  std::getline(ss, token, ' ');
  std::getline(ss, token, ' ');
  const auto element = token;

  elementRules.insert(std::make_pair(rule, element));
}

void applyRules() {
  std::string current;
  for (auto i = 0u; i < polymerTemplate.length(); ++i) {
    if (i + 1 == polymerTemplate.length()) {
      current += polymerTemplate[i];
      break;
    }

    const std::string pair =
        std::string() + polymerTemplate[i] + polymerTemplate[i + 1];
    if (exists(pair)) {
      auto insertion = elementRules.find(pair)->second;
      current = current + polymerTemplate[i] + insertion;
    } else {
      current = current + polymerTemplate[i];
    }
  }

  // std::cout << "New string is " << current;
  // std::cout << std::endl;
  polymerTemplate = current;
}

struct Element {
  Element(const std::string &id, const unsigned int &count)
      : id(id), count(count) {}
  const std::string id;
  const unsigned int count;
};

const bool compareByCount(const Element *left, const Element *right) {
  return left->count < right->count;
}

inline std::vector<const Element *> *countElementOccurrences() {
  auto elementCounts = std::map<const std::string, unsigned int>();
  auto occurrences = new std::vector<const Element *>();

  for (auto i = 0u; i < polymerTemplate.length(); ++i) {
    auto key = std::string() + polymerTemplate[i];
    if (elementCounts.find(key) == elementCounts.end()) {
      elementCounts.insert(std::make_pair(key, 1u));
    } else {
      elementCounts[key] += 1u;
    }
  }

  auto it = elementCounts.begin();
  while (it != elementCounts.end()) {
    occurrences->push_back(new Element(it->first, it->second));
    it++;
  }

  return occurrences;
}

int main() {
  std::ifstream infile("../resources/input");
  std::string line;

  std::getline(infile, line);
  polymerTemplate.append(line);

  while (std::getline(infile, line)) {
    if (!line.empty()) {
      parseInsertionPair(line);
    }
  }

  // std::cout << "Polymer template " << polymerTemplate << std::endl;
  // auto it = elementRules.begin();
  // while (it != elementRules.end()) {
  //   std::string rule = it->first;
  //   std::string element = it->second;

  //   std::cout << "Rule " << rule << ": " << element << std::endl;
  //   it++;
  // }

  for (auto i = 0u; i < 10; ++i) {
    applyRules();
  }

  auto elementCounts = countElementOccurrences();
  std::sort(elementCounts->begin(), elementCounts->end(), compareByCount);
  auto max = elementCounts->at(elementCounts->size() - 1)->count;
  auto min = elementCounts->at(0)->count;

  std::cout << elementCounts->at(0)->id << " " << elementCounts->at(0)->count
            << std::endl;
  std::cout << elementCounts->at(elementCounts->size() - 1)->id << " "
            << elementCounts->at(elementCounts->size() - 1)->count << std::endl;

  std::cout << "Result is " << (max - min) << std::endl;

  // std::cout << polymerTemplate.length() << std::endl;
  return 0;
}