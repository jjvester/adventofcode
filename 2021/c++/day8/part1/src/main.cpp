#include <algorithm>
#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>

auto output = new std::vector<std::string>();

const auto ONE_LENGTH = 2;
const auto FOUR_LENGTH = 4;
const auto SEVEN_LENGTH = 3;
const auto EIGHT_LENGTH = 7;

struct Output {
  std::string first;
  std::string second;
  std::string third;
  std::string foruth;
};

void split(const std::string &src) {
  std::stringstream ss(src);

  std::string token;
  while (std::getline(ss, token, ' ')) {
    if (!token.empty()) {
      std::sort(token.begin(), token.end());
      output->push_back(token);
    }
  }
}

void part1() {
  int counts = 0;
  for (auto elem : *output) {
    if (elem.length() == ONE_LENGTH || elem.length() == FOUR_LENGTH ||
        elem.length() == SEVEN_LENGTH || elem.length() == EIGHT_LENGTH) {
      counts += 1;
    }
  }

  std::cout << "Digits one, four, seven and eight appear " << counts
            << std::endl;
}

int main() {

  std::string input;
  std::ifstream infile("../resources/input");
  std::string record;

  while (std::getline(infile, record)) {
    if (!record.empty()) {
      std::stringstream ss(record);
      std::string token;

      // 10 digit input
      std::getline(ss, token, '|');

      // 4 digit output
      std::getline(ss, token, '|');
      split(token);
    }
  }

  for (auto elem : *output) {
    std::cout << elem << std::endl;
  }

  part1();

  return 0;
}
