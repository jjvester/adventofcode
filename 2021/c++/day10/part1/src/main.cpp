#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <stack>
#include <string>

const auto OPEN_CURLY = '{';
const auto CLOSE_CURLY = '}';

const auto OPEN_BRACKET = '(';
const auto CLOSE_BRACKET = ')';

const auto OPEN_SQUARE = '[';
const auto CLOSE_SQUARE = ']';

const auto OPEN_ANGULAR = '<';
const auto CLOSE_ANGULAR = '>';

auto total = 0l;

bool isOpenChunk(const char &elem) {
  return elem == OPEN_CURLY || elem == OPEN_BRACKET || elem == OPEN_SQUARE ||
         elem == OPEN_ANGULAR;
}

void check(const std::string &src,
           const std::map<const char, const char> &chunkPairs,
           const std::map<const char, unsigned int> &points) {
  auto stack = new std::stack<char>();
  for (unsigned int i = 0; i < src.length(); ++i) {
    auto current = src[i];
    if (isOpenChunk(current)) {
      stack->push(current);
    } else {
      const auto top = stack->top();
      stack->pop();
      auto close = chunkPairs.find(top)->second;
      if (close != current) {
        auto score = points.find(current)->second;
        std::cout << "Found mismatch, closing tag " << current
                  << " for opening tag " << top << " giving " << score
                  << " points" << std::endl;
        total += score;
        break;
      }
    }
  }
}

int main() {

  const auto chunkPairs = new std::map<const char, const char>();
  chunkPairs->insert(std::make_pair(OPEN_BRACKET, CLOSE_BRACKET));
  chunkPairs->insert(std::make_pair(OPEN_SQUARE, CLOSE_SQUARE));
  chunkPairs->insert(std::make_pair(OPEN_CURLY, CLOSE_CURLY));
  chunkPairs->insert(std::make_pair(OPEN_ANGULAR, CLOSE_ANGULAR));

  const auto points = new std::map<const char, unsigned int>();
  points->insert(std::make_pair(CLOSE_BRACKET, 3));
  points->insert(std::make_pair(CLOSE_SQUARE, 57));
  points->insert(std::make_pair(CLOSE_CURLY, 1197));
  points->insert(std::make_pair(CLOSE_ANGULAR, 25137));

  std::ifstream infile("../resources/input.txt");
  std::string line;
  while (std::getline(infile, line)) {
    if (!line.empty()) {
      check(line, *chunkPairs, *points);
    }
  }

  std::cout << "Points " << total << std::endl;

  return 0;
}