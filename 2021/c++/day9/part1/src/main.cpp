#include <algorithm>
#include <fstream>
#include <iostream>
#include <sstream>
#include <stack>
#include <string>
#include <vector>

const auto ROW_LENGTH = 100u;
// const auto ROW_LENGTH = 10u;
const auto PLATEAU_HEIGHT = 9u;

struct Cell {
  Cell(const unsigned &idx, const unsigned int &height)
      : idx(idx), height(height), visited(false) {}

  const bool operator=(const Cell &other) const { return other.idx == idx; }
  const unsigned int idx;
  const unsigned int height;
  bool visited;
  std::vector<Cell *> *neighbors;
};

auto grid = new std::vector<std::vector<Cell *> *>();
auto basins = new std::vector<std::vector<Cell *> *>();

void setRow(const std::string &src, const unsigned int &rowIdx) {
  auto row = new std::vector<Cell *>();
  for (unsigned int i = 0; i < ROW_LENGTH; ++i) {
    auto cell = new Cell(rowIdx == 0 ? i : i + rowIdx, (src[i] - 48u));
    row->push_back(cell);
  }

  grid->push_back(row);
}

bool isNorth(const Cell &cell) { return cell.idx < ROW_LENGTH; }

bool isSouth(const Cell &cell) {
  return cell.idx < grid->size() * ROW_LENGTH &&
         cell.idx > ((grid->size() * ROW_LENGTH) - ROW_LENGTH);
}

bool isWest(const Cell &cell) { return cell.idx % ROW_LENGTH == 0; }

bool isEast(const Cell &cell) {
  return cell.idx % ROW_LENGTH == (ROW_LENGTH - 1);
}

bool isNorthWest(const Cell &cell) { return cell.idx == 0; }

bool isNorthEast(const Cell &cell) { return cell.idx == (ROW_LENGTH - 1); }

bool isSouthWest(const Cell &cell) {
  return cell.idx == (grid->size() * ROW_LENGTH) - ROW_LENGTH;
}

bool isSouthEast(const Cell &cell) {
  return cell.idx == (grid->size() * ROW_LENGTH) - 1;
}

bool isLowest(const Cell &cell, std::vector<Cell *> *neighbors) {
  for (auto neighbor : *neighbors) {
    if (cell.height >= neighbor->height) {
      return false;
    }
  }

  return true;
}

std::vector<Cell *> *getAdjacent(Cell &cell) {
  cell.neighbors = new std::vector<Cell *>();

  if (isNorthWest(cell)) {
    cell.neighbors->push_back(grid->at(0)->at(1));
    cell.neighbors->push_back(grid->at(1)->at(0));
  } else if (isNorthEast(cell)) {
    cell.neighbors->push_back(grid->at(0)->at(ROW_LENGTH - 2));
    cell.neighbors->push_back(grid->at(1)->at(ROW_LENGTH - 1));
  } else if (isSouthWest(cell)) {
    cell.neighbors->push_back(grid->at(grid->size() - 1)->at(1));
    cell.neighbors->push_back(grid->at(grid->size() - 2)->at(0));
  } else if (isSouthEast(cell)) {
    cell.neighbors->push_back(grid->at(grid->size() - 1)->at(ROW_LENGTH - 2));
    cell.neighbors->push_back(grid->at(grid->size() - 2)->at(ROW_LENGTH - 1));
  } else if (isNorth(cell)) {
    cell.neighbors->push_back(grid->at(0)->at(cell.idx - 1));
    cell.neighbors->push_back(grid->at(0)->at(cell.idx + 1));
    cell.neighbors->push_back(grid->at(1)->at(cell.idx % ROW_LENGTH));
  } else if (isSouth(cell)) {
    cell.neighbors->push_back(
        grid->at(grid->size() - 1)->at((cell.idx % ROW_LENGTH) - 1));
    cell.neighbors->push_back(
        grid->at(grid->size() - 1)->at((cell.idx % ROW_LENGTH) + 1));
    cell.neighbors->push_back(
        grid->at(grid->size() - 2)->at(cell.idx % ROW_LENGTH));
  } else if (isWest(cell)) {
    cell.neighbors->push_back(grid->at(cell.idx / ROW_LENGTH - 1)->at(0));
    cell.neighbors->push_back(grid->at(cell.idx / ROW_LENGTH + 1)->at(0));
    cell.neighbors->push_back(grid->at(cell.idx / ROW_LENGTH)->at(1));
  } else if (isEast(cell)) {
    cell.neighbors->push_back(
        grid->at(cell.idx / ROW_LENGTH - 1)->at(ROW_LENGTH - 1));
    cell.neighbors->push_back(
        grid->at(cell.idx / ROW_LENGTH + 1)->at(ROW_LENGTH - 1));
    cell.neighbors->push_back(
        grid->at(cell.idx / ROW_LENGTH)->at(ROW_LENGTH - 2));
  } else {
    cell.neighbors->push_back(
        grid->at(cell.idx / ROW_LENGTH)->at((cell.idx % ROW_LENGTH) - 1));
    cell.neighbors->push_back(
        grid->at(cell.idx / ROW_LENGTH)->at((cell.idx % ROW_LENGTH) + 1));
    cell.neighbors->push_back(
        grid->at(cell.idx / ROW_LENGTH - 1)->at(cell.idx % ROW_LENGTH));
    cell.neighbors->push_back(
        grid->at(cell.idx / ROW_LENGTH + 1)->at(cell.idx % ROW_LENGTH));
  }

  return cell.neighbors;
}

std::vector<Cell *> *mapBasin(Cell &cell) {
  auto basin = new std::vector<Cell *>();
  auto stack = new std::stack<Cell *>();
  stack->push(&cell);

  while (!stack->empty()) {
    auto elem = stack->top();
    stack->pop();

    if (!elem->visited && elem->height < PLATEAU_HEIGHT) {
      for (auto neighbor : *(elem->neighbors)) {
        stack->push(neighbor);
      }

      basin->push_back(elem);
    }

    elem->visited = true;
  }

  return basin;
}

int main() {

  std::string input;
  std::ifstream infile("../resources/input");
  std::string record;

  auto rowIdx = 0u;
  while (std::getline(infile, record)) {
    if (!record.empty()) {
      std::cout << record << std::endl;
      setRow(record, rowIdx);
    }

    rowIdx += ROW_LENGTH;
  }

  auto lowest = new std::vector<Cell *>();
  auto risk = 0u;
  for (auto row : *grid) {
    for (auto cell : *row) {
      auto neighbors = getAdjacent(*cell);
      if (isLowest(*cell, neighbors)) {
        risk += cell->height + 1;
        lowest->push_back(cell);
      }
      // std::cout << cell->idx << ":" << cell->height << "  ";
    } // std::cout << std::endl;
  }

  // std::cout << "Total risk is " << risk << std::endl;
  // for (auto cell : *lowest) {
  //   std::cout << cell->idx << " : " << cell->height << std::endl;
  // }

  for (unsigned int i = 0; i < lowest->size(); ++i) {
    auto current = lowest->at(i);
    if (!current->visited) {
      basins->push_back(mapBasin(*current));
    }
  }

  std::vector<int> basinSizes(basins->size());
  // std::cout << "Total basins " << basins->size() << std::endl;
  for (auto basin : *basins) {
    // std::cout << "Basin size " << basin->size() << std::endl;
    basinSizes.push_back(basin->size());
  }

  std::sort(basinSizes.begin(), basinSizes.end());
  for (auto elem : basinSizes) {
    std::cout << elem << std::endl;
  }

  std::cout << (basinSizes.at(basinSizes.size() - 3) *
                basinSizes.at(basinSizes.size() - 2) *
                basinSizes.at(basinSizes.size() - 1))
            << std::endl;
  // 45 * 52 * 72

  return 0;
}
