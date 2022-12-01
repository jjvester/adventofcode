#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <stack>
#include <string>
#include <vector>

const auto OPEN_CURLY = '{';
const auto CLOSE_CURLY = '}';

const auto OPEN_BRACKET = '(';
const auto CLOSE_BRACKET = ')';

const auto OPEN_SQUARE = '[';
const auto CLOSE_SQUARE = ']';

const auto OPEN_ANGULAR = '<';
const auto CLOSE_ANGULAR = '>';

struct Result {
  Result(const std::string &original, const std::string &remaining,
         const std::string &needed, const long &score)
      : original(original), remaining(remaining), needed(needed), score(score) {
  }

  std::string original;
  std::string remaining;
  std::string needed;
  long score;
};

auto results = new std::vector<Result *>();

bool isOpenChunk(const char &elem) {
  return elem == OPEN_CURLY || elem == OPEN_BRACKET || elem == OPEN_SQUARE ||
         elem == OPEN_ANGULAR;
}

long check(const std::string &src,
           const std::map<const char, const char> &chunkPairs,
           const std::map<const char, long> &points) {
  auto stack = std::stack<char>();
  bool corrupted = false;
  for (unsigned int i = 0; i < src.length(); ++i) {
    auto current = src[i];
    if (isOpenChunk(current)) {
      stack.push(current);
    } else {
      const auto top = stack.top();
      stack.pop();
      auto close = chunkPairs.find(top)->second;
      if (close != current) {
        stack = std::stack<char>(); // empty stack
        break;                      // ignore corrupted lines
      }
    }
  }

  auto lineScore = 0l;
  std::string closeString;
  std::string openString;

  while (!stack.empty()) {
    auto item = stack.top();
    stack.pop();
    auto close = chunkPairs.find(item)->second;

    openString += item;
    closeString += close;

    lineScore *= 5;
    lineScore += points.find(close)->second;
  }

  results->push_back(new Result(src, openString, closeString, lineScore));
  return lineScore;
}

int main() {

  const auto chunkPairs = new std::map<const char, const char>();
  chunkPairs->insert(std::make_pair(OPEN_BRACKET, CLOSE_BRACKET));
  chunkPairs->insert(std::make_pair(OPEN_SQUARE, CLOSE_SQUARE));
  chunkPairs->insert(std::make_pair(OPEN_CURLY, CLOSE_CURLY));
  chunkPairs->insert(std::make_pair(OPEN_ANGULAR, CLOSE_ANGULAR));

  const auto points = new std::map<const char, long>();
  points->insert(std::make_pair(CLOSE_BRACKET, 1));
  points->insert(std::make_pair(CLOSE_SQUARE, 2));
  points->insert(std::make_pair(CLOSE_CURLY, 3));
  points->insert(std::make_pair(CLOSE_ANGULAR, 4));

  auto scores = new std::vector<long>();
  std::ifstream infile("../resources/input.txt");
  std::string line;
  while (std::getline(infile, line)) {
    if (!line.empty()) {
      auto result = check(line, *chunkPairs, *points);
      if (result > 0) {
        scores->push_back(result);
      }
    }
  }

  std::sort(scores->begin(), scores->end());
  // for (auto score : *scores) {
  //   std::cout << score << std::endl;
  // }

  auto middle = (scores->size() / 2);
  std::cout << "Scores size " << scores->size() << std::endl;
  std::cout << "Scores middle " << middle << std::endl;
  std::cout << "Middle score is " << scores->at(middle) << std::endl;

  // for (auto result : *results) {
  //   std::cout << "Original " << result->original << std::endl;
  //   std::cout << "Remaining " << result->remaining << std::endl;
  //   std::cout << "Needed " << result->needed << std::endl;
  //   std::cout << "*************" << std::endl;
  // }

  return 0;
}