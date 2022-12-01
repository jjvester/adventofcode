#include <fstream>
#include <iostream>
#include <stack>
#include <string>
#include <vector>

const unsigned int ROW_LENGTH = 10;
auto totalFlashes = 0u;

struct Octopus {
  Octopus(const unsigned int &idx, const unsigned int &energy)
      : idx(idx), energy(energy) {}
  const unsigned int idx;
  unsigned int energy;
  std::vector<Octopus *> *neighbors;
  bool flash = false;
};

struct Cavern {
  Cavern(const std::vector<std::vector<Octopus *>> &cavern) : cavern(cavern) {}
  std::vector<std::vector<Octopus *>> cavern;

  bool isNorth(const Octopus &octopus) { return octopus.idx < ROW_LENGTH; }

  bool isSouth(const Octopus &octopus) {
    return octopus.idx < cavern.size() * ROW_LENGTH &&
           octopus.idx > ((cavern.size() * ROW_LENGTH) - ROW_LENGTH);
  }

  bool isWest(const Octopus &octopus) { return octopus.idx % ROW_LENGTH == 0; }

  bool isEast(const Octopus &octopus) {
    return octopus.idx % ROW_LENGTH == (ROW_LENGTH - 1);
  }

  bool isNorthWest(const Octopus &octopus) { return octopus.idx == 0; }

  bool isNorthEast(const Octopus &octopus) {
    return octopus.idx == (ROW_LENGTH - 1);
  }

  bool isSouthWest(const Octopus &octopus) {
    return octopus.idx == (cavern.size() * ROW_LENGTH) - ROW_LENGTH;
  }

  bool isSouthEast(const Octopus &octopus) {
    return octopus.idx == (cavern.size() * ROW_LENGTH) - 1;
  }

  inline void init() {
    for (unsigned int i = 0; i < cavern.size(); ++i) {
      auto row = cavern.at(i);
      for (unsigned int j = 0; j < ROW_LENGTH; ++j) {
        auto octopus = row.at(j);

        octopus->neighbors = new std::vector<Octopus *>();

        if (isNorthWest(*octopus)) {
          octopus->neighbors->push_back(cavern.at(0).at(1));
          octopus->neighbors->push_back(cavern.at(1).at(0));
          octopus->neighbors->push_back(cavern.at(1).at(1));
        } else if (isNorthEast(*octopus)) {
          octopus->neighbors->push_back(cavern.at(0).at(ROW_LENGTH - 2));
          octopus->neighbors->push_back(cavern.at(1).at(ROW_LENGTH - 1));
          octopus->neighbors->push_back(cavern.at(1).at(ROW_LENGTH - 2));
        } else if (isSouthWest(*octopus)) {
          octopus->neighbors->push_back(cavern.at(cavern.size() - 1).at(1));
          octopus->neighbors->push_back(cavern.at(cavern.size() - 2).at(0));
          octopus->neighbors->push_back(cavern.at(cavern.size() - 2).at(1));
        } else if (isSouthEast(*octopus)) {
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 1).at(ROW_LENGTH - 2));
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 2).at(ROW_LENGTH - 1));
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 2).at(ROW_LENGTH - 2));
        } else if (isNorth(*octopus)) {
          octopus->neighbors->push_back(cavern.at(0).at(octopus->idx - 1));
          octopus->neighbors->push_back(cavern.at(0).at(octopus->idx + 1));
          octopus->neighbors->push_back(
              cavern.at(1).at(octopus->idx % ROW_LENGTH));
          octopus->neighbors->push_back(
              cavern.at(1).at((octopus->idx % ROW_LENGTH) - 1));
          octopus->neighbors->push_back(
              cavern.at(1).at((octopus->idx % ROW_LENGTH) + 1));
        } else if (isSouth(*octopus)) {
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 1).at((octopus->idx % ROW_LENGTH) - 1));
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 1).at((octopus->idx % ROW_LENGTH) + 1));
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 2).at(octopus->idx % ROW_LENGTH));
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 2).at((octopus->idx % ROW_LENGTH) - 1));
          octopus->neighbors->push_back(
              cavern.at(cavern.size() - 2).at((octopus->idx % ROW_LENGTH) + 1));
        } else if (isWest(*octopus)) {
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH - 1).at(0));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH + 1).at(0));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH).at(1));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH - 1).at(1));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH + 1).at(1));
        } else if (isEast(*octopus)) {
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH - 1).at(ROW_LENGTH - 1));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH + 1).at(ROW_LENGTH - 1));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH).at(ROW_LENGTH - 2));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH - 1).at(ROW_LENGTH - 2));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH + 1).at(ROW_LENGTH - 2));
        } else {
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH)
                  .at((octopus->idx % ROW_LENGTH) - 1));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH)
                  .at((octopus->idx % ROW_LENGTH) + 1));
          octopus->neighbors->push_back(cavern.at(octopus->idx / ROW_LENGTH - 1)
                                            .at(octopus->idx % ROW_LENGTH));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH - 1)
                  .at((octopus->idx % ROW_LENGTH - 1)));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH - 1)
                  .at((octopus->idx % ROW_LENGTH + 1)));
          octopus->neighbors->push_back(cavern.at(octopus->idx / ROW_LENGTH + 1)
                                            .at(octopus->idx % ROW_LENGTH));

          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH + 1)
                  .at((octopus->idx % ROW_LENGTH) - 1));
          octopus->neighbors->push_back(
              cavern.at(octopus->idx / ROW_LENGTH + 1)
                  .at((octopus->idx % ROW_LENGTH) + 1));
        }
      }
    }
  }

  inline std::stack<Octopus *> *tick() {
    auto flashing = new std::stack<Octopus *>();
    for (auto row : cavern) {
      for (auto octopus : row) {
        octopus->energy += 1;
        if (octopus->energy > 9) {
          flashing->push(octopus);
        }
      }
    }
    return flashing;
  }

  inline void processFlash(std::stack<Octopus *> &flashers) {
    auto bookKeeping = std::vector<Octopus *>();
    while (!flashers.empty()) {
      auto top = flashers.top();
      flashers.pop();
      totalFlashes += 1;

      for (auto neighbor : *(top->neighbors)) {
        if (neighbor->energy <= 9) {
          neighbor->energy += 1;
          if (neighbor->energy > 9) {
            flashers.push(neighbor);
          }
        }
      }

      bookKeeping.push_back(top);
    }

    for (auto octopus : bookKeeping) {
      octopus->flash = false;
      octopus->energy = 0;
    }
  }
};

inline std::ostream &operator<<(std::ostream &out, const Octopus &octopus) {
  out << octopus.idx << ":" << octopus.energy << ":"
      << (octopus.flash ? "true" : "false") << "\t";
  return out;
}

inline std::ostream &operator<<(std::ostream &out, const Cavern &cavern) {
  for (auto row : cavern.cavern) {
    for (auto octopus : row) {
      std::cout << *octopus;
    }

    std::cout << std::endl;
  }

  return out;
}

inline std::vector<Octopus *> *split(const std::string &src,
                                     const unsigned int &rowIdx) {
  auto row = new std::vector<Octopus *>();
  for (unsigned int i = 0; i < src.length(); ++i) {
    row->push_back(new Octopus(rowIdx + i, src[i] - 48));
  }

  return row;
}

int main() {
  auto rows = std::vector<std::vector<Octopus *>>();

  std::ifstream infile("../resources/input");
  std::string line;
  auto rowIdx = 0;
  while (std::getline(infile, line)) {
    rows.push_back(*split(line, rowIdx));
    rowIdx += 10;
  }

  auto cavern = Cavern(rows);
  cavern.init();

  for (unsigned int day = 0; day < 100; ++day) {
    std::cout << "Before tick " << std::endl << cavern << std::endl;
    auto flashing = cavern.tick();
    cavern.processFlash(*flashing);

    std::cout << "After flash " << std::endl << cavern << std::endl;
    std::cout << "Total flashes after day " << (day + 1) << " " << totalFlashes
              << std::endl;
  }

  return 0;
}