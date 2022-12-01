#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>

std::string polymerTemplate;
auto elementRules = std::map<const std::string, const std::string>();
const auto chunks = 5u;

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

void doChunk(std::string *chunk, const long long &currentIndex) {
  const std::string pair = std::string() + polymerTemplate[currentIndex] +
                           polymerTemplate[currentIndex + 1];

  if (exists(pair)) {
    auto insertion = elementRules.find(pairA)->second;
    *chunk = *chunk + polymerTemplate[currentIndex] + insertion;
  } else {
    *chunk = *chunk + polymerTemplate[i];
  }
}

void doMerge(const std::vector<std::string> &src) {
  std::string merged = src.at(0);
  for (auto i = 1ll; i < src.size(); ++i) {
    auto next = src.at(i);
    auto pair = merged.at(merged.length() - 1) + next.at(0);
    if (exists(pair)) {
      auto insertion = elementRules.find(pair)->second;
      merged += insertion;
    } else {
      merged += next;
    }
  }

  polymerTemplate = merged;
}

void applyRules() {
  long long length = polymerTemplate.length();

  if (lenth > 100) {
    auto boundary = length / 10;
    auto parts = new std::vector<std::string>(10);

    for (auto i = oll; i < boundary; ++i) {
      doChunk(parts->at(0));
    }
  }

  std::string partA;
  std::string partB;

  for (auto i = 0ll; i < boundary; ++i) {
    if (i + 1 < boundary) {
      const std::string pairA =
          std::string() + polymerTemplate[i] + polymerTemplate[i + 1];

      if (exists(pairA)) {
        auto insertion = elementRules.find(pairA)->second;
        partA = partA + polymerTemplate[i] + insertion;
      } else {
        partA = partA + polymerTemplate[i];
      }
    } else {
      partA = partA + polymerTemplate[i];
    }

    const std::string pairB = std::string() + polymerTemplate[i + boundary] +
                              polymerTemplate[i + boundary + 1];

    if (exists(pairB)) {
      auto insertion = elementRules.find(pairB)->second;
      partB = partB + polymerTemplate[i + boundary] + insertion;
      if (i + 1 == boundary) {
        partB = partB + polymerTemplate[i + boundary + 1];
      }
    } else {
      partB = partB + polymerTemplate[i + boundary];
    }
  }

  // Check on merge
  std::string mergePair(1, partA.at(partA.length() - 1));
  mergePair += partB.at(0);
  if (exists(mergePair)) {
    auto insertion = elementRules.find(mergePair)->second;
    partA += insertion;
  }

  polymerTemplate = partA + partB;
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

  for (auto i = 0u; i < 40; ++i) {
    applyRules();
  }

  auto bCount = 0ll;
  auto hCount = 0ll;
  for (auto i = 0ll; i < polymerTemplate.length(); ++i) {
    auto item = polymerTemplate[i];
    if (item == 'B') {
      bCount++;
    }

    if (item == 'H') {
      hCount++;
    }
  }

  std::cout << "H count " << hCount << std::endl;
  std::cout << "B count " << bCount << std::endl;
  // std::cout << polymerTemplate << std::endl;

  return 0;
}