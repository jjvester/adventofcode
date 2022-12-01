#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>

std::string polymerTemplate;
auto elementRules = std::map<const std::string, const std::string>();
auto polymer = new std::map<const std::string, long long>();

bool ruleExists(const std::string &key) {
  return elementRules.find(key) != elementRules.end();
}

bool polymerElementExists(const std::string &key) {
  return polymer->find(key) != polymer->end();
}

bool exists(const std::string &key,
            std::map<const std::string, long long> &store) {
  return store.find(key) != store.end();
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

std::map<const std::string, long long> *copyPolymer() {
  auto copy = new std::map<const std::string, long long>();
  auto it = polymer->begin();
  while (it != polymer->end()) {
    copy->insert(std::make_pair(it->first, it->second));
    it++;
  }

  return copy;
}

std::map<const std::string, long long> *applyRules() {
  auto copy = copyPolymer();
  auto it = elementRules.begin();
  while (it != elementRules.end()) {
    std::string rule = it->first;
    std::string element = it->second;

    if (polymerElementExists(rule)) {
      const std::string partA = std::string(1, rule[0]);
      const std::string partB = std::string(1, rule[1]);
      const std::string keyA = partA + element;
      const std::string keyB = element + partB;

      if (exists(keyA, *copy)) {
        (*copy)[keyA] += 1l;
      } else {
        copy->insert(std::make_pair(keyA, 1l));
      }

      if (exists(keyB, *copy)) {
        (*copy)[keyB] += 1l;
      } else {
        copy->insert(std::make_pair(keyB, 1l));
      }

      auto keyTotal = copy->find(rule)->second;
      keyTotal -= 1l;
      copy->erase(rule);

      if (keyTotal > 0) {
        copy->insert(std::make_pair(rule, keyTotal));
      }
    }

    it++;
  }

  return copy;
}

void debug() {
  auto totals = std::map<const std::string, long long>();
  auto it = polymer->begin();
  long long grandTotal = 0;
  while (it != polymer->end()) {
    auto element = it->first;
    auto count = it->second;

    const std::string partA = std::string(1, element[0]);
    const std::string partB = std::string(1, element[1]);

    std::cout << "Element " << element << std::endl;
    std::cout << "Count " << count << std::endl;

    if (totals.find(partA) != totals.end()) {
      totals[partA] += count;
    } else {
      totals.insert(std::make_pair(partA, count));
    }

    if (totals.find(partB) != totals.end()) {
      totals[partB] += count;
    } else {
      totals.insert(std::make_pair(partB, count));
    }

    grandTotal += count;

    it++;
  }

  auto itt = totals.begin();
  while (itt != totals.end()) {
    auto element = itt->first;
    auto count = itt->second;

    std::cout << "Element " << element << " : " << count << std::endl;
    itt++;
  }

  std::cout << "Grand total " << grandTotal << std::endl;
}

void toPairs() {
  for (auto i = 0u; i < polymerTemplate.length(); ++i) {
    if (i + 1 == polymerTemplate.length()) {
      // const std::string toAdd(1, polymerTemplate[i]);
      // if (!polymerElementExists(toAdd)) {
      //   polymer.insert(std::make_pair(toAdd, 1));
      // }
      break;
    }

    const std::string pair =
        std::string() + polymerTemplate[i] + polymerTemplate[i + 1];
    if (!polymerElementExists(pair)) {
      polymer->insert(std::make_pair(pair, 1l));
    }
  }
}

int main() {
  std::ifstream infile("../resources/other");
  std::string line;

  std::getline(infile, line);
  polymerTemplate.append(line);

  while (std::getline(infile, line)) {
    if (!line.empty()) {
      parseInsertionPair(line);
    }
  }

  toPairs();

  for (auto i = 0u; i < 10; ++i) {
    auto updated = applyRules();
    delete polymer;
    polymer = updated;
    // debug();
  }

  debug();

  return 0;
}