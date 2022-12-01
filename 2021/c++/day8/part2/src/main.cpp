#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <set>
#include <sstream>
#include <string>
#include <vector>

auto patterns = new std::map<std::string, std::string>();
auto input = new std::set<std::string>();
auto output = new std::vector<std::string>();

bool exists(const std::string &key) {
  return patterns->find(key) != patterns->end();
}

int splitOutput(const std::string &src) {
  std::stringstream ss(src);
  std::string result;
  std::string token;
  while (std::getline(ss, token, ' ')) {
    if (!token.empty()) {
      std::sort(token.begin(), token.end());
      if (exists(token)) {
        auto found = patterns->find(token)->second;
        result.append(found);
      }
    }
  }

  std::cout << result << std::endl;
  return result.empty() ? 0 : std::stoi(result);
}

void splitInput(const std::string &src) {
  std::stringstream ss(src);

  std::string token;
  while (std::getline(ss, token, ' ')) {
    if (!token.empty()) {
      std::sort(token.begin(), token.end());
      if (!exists(token)) {
        input->insert(token);
      }
    }
  }
}

int main() {

  std::string EIGHT = "acedgfb";
  patterns->insert(std::make_pair(EIGHT, "8"));

  std::string FIVE = "cdfbe";
  patterns->insert(std::make_pair(FIVE, "5"));

  std::string TWO = "gcdfa";
  patterns->insert(std::make_pair(TWO, "2"));

  std::string THREE = "fbcad";
  patterns->insert(std::make_pair(THREE, "3"));

  std::string SEVEN = "dab";
  patterns->insert(std::make_pair(SEVEN, "7"));

  std::string NINE = "cefabd";
  patterns->insert(std::make_pair(NINE, "9"));

  std::string SIX = "cdfgeb";
  patterns->insert(std::make_pair(SIX, "6"));

  std::string FOUR = "eafb";
  patterns->insert(std::make_pair(FOUR, "4"));

  std::string ZERO = "cagedb";
  patterns->insert(std::make_pair(ZERO, "0"));

  std::string ONE = "ab";
  patterns->insert(std::make_pair(ONE, "1"));

  std::string input;
  std::ifstream infile("../resources/other");
  std::string record;

  long total = 0;

  while (std::getline(infile, record)) {
    if (!record.empty()) {
      std::stringstream ss(record);
      std::string token;

      // // 10 digit input
      // std::getline(ss, token, '|');
      // // splitInput(token);

      // 4 digit output
      // int index = 0;
      // while (std::getline(ss, token, '|')) {
      //   if (index > 0) {
      //     int number = splitOutput(token);
      //     // std::cout << number << std::endl;
      //     total += number;
      //   }

      //   index++;
      // }
    }
  }

  std::map<std::string, std::string>::iterator it = patterns->begin();
  while (it != patterns->end()) {
    std::cout << it->first << " " << it->second << std::endl;
    it++;
  }

  std::cout << "Total is " << total << std::endl;
  // for (auto elem : *output) {
  //   std::cout << elem << std::endl;
  // }

  return 0;
}
