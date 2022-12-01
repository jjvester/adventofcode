#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

const auto ZERO = '0';
const auto ONE = '1';

std::string filter(std::vector<std::string> *items, int index,
                   bool mostMatches) {
  if (items->size() == 1) {
    return items->at(0);
  }

  auto *ones = new std::vector<std::string>();
  auto *zeros = new std::vector<std::string>();

  for (int i = 0; i < items->size(); ++i) {
    if (items->at(i)[index] == ZERO) {
      zeros->push_back(items->at(i));
    } else {
      ones->push_back(items->at(i));
    }
  }

  if (mostMatches) {
    if (ones->size() > zeros->size()) {
      return filter(ones, index + 1, mostMatches);
    } else if (zeros->size() > ones->size()) {
      return filter(zeros, index + 1, mostMatches);
    } else {
      return filter(ones, index + 1, mostMatches);
    }
  } else {
    if (ones->size() < zeros->size()) {
      return filter(ones, index + 1, mostMatches);
    } else if (zeros->size() > ones->size()) {
      return filter(zeros, index + 1, mostMatches);
    } else {
      return filter(zeros, index + 1, mostMatches);
    }
  }
}

int main() {

  std::vector<std::string> *items = new std::vector<std::string>();

  std::string binaryInput;
  std::ifstream infile("../resources/input");
  std::string line;

  while (std::getline(infile, line)) {
    std::istringstream iss(line);
    iss >> binaryInput;
    items->push_back(binaryInput);
  }

  long o2GeneratorReading = stol(filter(items, 0, true), 0, 2);
  long co2ScrubberReading = stol(filter(items, 0, false), 0, 2);

  std::cout << "Life support rating: "
            << (o2GeneratorReading * co2ScrubberReading) << std::endl;
}
