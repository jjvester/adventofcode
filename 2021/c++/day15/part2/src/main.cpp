#include <algorithm>
#include <climits>
#include <fstream>
#include <iostream>
#include <set>
#include <stack>
#include <string>
#include <tuple>
#include <vector>

struct Point {
  Point(const int &id, const int &cost) : id(id), cost(cost) {}

  const int id;
  const int cost;
  int travelCost = INT_MAX;
  std::vector<Point *> *neighbors;

  bool operator=(const Point *other) const { return id == other->id; }
  bool operator<(const Point *other) const { return id < other->id; }
};

auto currentTotalCost = 0;
auto currentTotalCostSet = false;

unsigned int initialRow = 100;
unsigned int initialCol = 100;
unsigned int maxRow = 500;
unsigned int maxCol = 500;

auto grid = new std::vector<std::vector<Point *> *>();
auto paths = new std::vector<std::tuple<int, std::vector<Point *> *>>();

inline bool exists(const int &key, std::set<int> &visited) { return visited.find(key) != visited.end(); }

inline unsigned int toId(const int &x, const int &y) { return y == 0 ? x : (y * maxCol) + x; }

bool isNorth(const Point &point) { return point.id < maxCol; }

bool isSouth(const Point &point) { return point.id < grid->size() * maxCol && point.id > ((grid->size() * maxCol) - maxCol); }

bool isWest(const Point &point) { return point.id % maxCol == 0; }

bool isEast(const Point &point) { return point.id % maxCol == (maxCol - 1); }

bool isNorthWest(const Point &point) { return point.id == 0; }

bool isNorthEast(const Point &point) { return point.id == (maxCol - 1); }

bool isSouthWest(const Point &point) { return point.id == (grid->size() * maxCol) - maxCol; }

bool isSouthEast(const Point &point) { return point.id == (grid->size() * maxCol) - 1; }

inline void setNeighbors() {
  for (auto row : *grid) {
    for (auto point : *row) {
      point->neighbors = new std::vector<Point *>();

      if (isNorthWest(*point)) {
        point->neighbors->push_back(grid->at(0)->at(1));
        point->neighbors->push_back(grid->at(1)->at(0));
      } else if (isNorthEast(*point)) {
        point->neighbors->push_back(grid->at(0)->at(maxCol - 2));
        point->neighbors->push_back(grid->at(1)->at(maxCol - 1));
      } else if (isSouthWest(*point)) {
        point->neighbors->push_back(grid->at(grid->size() - 1)->at(1));
        point->neighbors->push_back(grid->at(grid->size() - 2)->at(0));
      } else if (isSouthEast(*point)) {
        point->neighbors->push_back(grid->at(grid->size() - 1)->at(maxCol - 2));
        point->neighbors->push_back(grid->at(grid->size() - 2)->at(maxCol - 1));
      } else if (isNorth(*point)) {
        point->neighbors->push_back(grid->at(0)->at(point->id - 1));
        point->neighbors->push_back(grid->at(0)->at(point->id + 1));
        point->neighbors->push_back(grid->at(1)->at(point->id % maxCol));
      } else if (isSouth(*point)) {
        point->neighbors->push_back(grid->at(grid->size() - 1)->at((point->id % maxCol) - 1));
        point->neighbors->push_back(grid->at(grid->size() - 1)->at((point->id % maxCol) + 1));
        point->neighbors->push_back(grid->at(grid->size() - 2)->at(point->id % maxCol));
      } else if (isWest(*point)) {
        point->neighbors->push_back(grid->at(point->id / maxCol - 1)->at(0));
        point->neighbors->push_back(grid->at(point->id / maxCol + 1)->at(0));
        point->neighbors->push_back(grid->at(point->id / maxCol)->at(1));
      } else if (isEast(*point)) {
        point->neighbors->push_back(grid->at(point->id / maxCol - 1)->at(maxCol - 1));
        point->neighbors->push_back(grid->at(point->id / maxCol + 1)->at(maxCol - 1));
        point->neighbors->push_back(grid->at(point->id / maxCol)->at(maxCol - 2));
      } else {
        point->neighbors->push_back(grid->at(point->id / maxCol)->at((point->id % maxCol) - 1));
        point->neighbors->push_back(grid->at(point->id / maxCol)->at((point->id % maxCol) + 1));
        point->neighbors->push_back(grid->at(point->id / maxCol - 1)->at(point->id % maxCol));
        point->neighbors->push_back(grid->at(point->id / maxCol + 1)->at(point->id % maxCol));
      }
    }
  }
}

bool isVisited(const Point &point, std::vector<Point *> *path) {
  for (auto elem : *path) {
    if (elem->id == point.id) {
      return true;
    }
  }
  return false;
}

bool isNeighborWorthy(const Point &origin, const Point &neighbor, std::vector<Point *> *path) {
  return origin.travelCost + neighbor.cost < neighbor.travelCost && !isVisited(neighbor, path);
}

void relaxNeighbor(const Point &origin, Point &neighbor) { neighbor.travelCost = origin.travelCost + neighbor.cost; }

std::vector<Point *> *copy(std::vector<Point *> *src) {
  auto result = new std::vector<Point *>();
  for (auto elem : *src) {
    result->push_back(elem);
  }
  return result;
}

bool costAcceptable(const int &costSoFar) { return !currentTotalCostSet || costSoFar < currentTotalCost; }

void next(Point *current, Point *destination, std::vector<Point *> *path, int costSoFar) {
  if (current->id != destination->id && costAcceptable(costSoFar)) {
    for (auto neighbor : *current->neighbors) {
      if (isNeighborWorthy(*current, *neighbor, path)) {
        relaxNeighbor(*current, *neighbor);
        auto breadcrumbs = copy(path);
        breadcrumbs->push_back(neighbor);
        next(neighbor, destination, breadcrumbs, current->cost + costSoFar);
      }
    }
  } else if (current->id == destination->id) {
    int cost = 0;
    for (auto point : *path) {
      cost += point->cost;
    }

    paths->push_back(std::make_tuple(cost, path));
    if (!currentTotalCostSet) {
      currentTotalCostSet = true;
      currentTotalCost = cost;
    } else {
      currentTotalCost = std::min(currentTotalCost, cost);
    }
  }

  delete path;
}

void navigate(Point &source, Point &destination, int cost) {
  auto path = new std::vector<Point *>();
  source.travelCost = cost;
  path->push_back(&source);
  next(&source, &destination, path, cost);
}

inline void addRow(const std::string &src, const int &rowIndex) {
  auto row = new std::vector<Point *>();
  for (auto i = 0; i < src.length(); ++i) {
    row->push_back(new Point(toId(i, rowIndex), src[i] - 48));
  }

  grid->push_back(row);
}

inline void enlargYAxis() {
  for (auto rowIndex = initialRow; rowIndex < maxRow; ++rowIndex) {
    auto row = new std::vector<Point *>();
    for (auto colIndex = 0u; colIndex < maxCol; ++colIndex) {
      auto cost = grid->at(rowIndex - initialCol)->at(colIndex)->cost == 9 ? 1 : grid->at(rowIndex - initialCol)->at(colIndex)->cost + 1;
      row->push_back(new Point(toId(colIndex, rowIndex), cost));
    }
    grid->push_back(row);
  }
}

inline void enlargeXaxis() {
  for (auto rowIndex = 0u; rowIndex < grid->size(); ++rowIndex) {
    for (auto colIndex = initialCol; colIndex < maxCol; ++colIndex) {
      auto cost = grid->at(rowIndex)->at(colIndex - initialCol)->cost == 9 ? 1 : grid->at(rowIndex)->at(colIndex - initialCol)->cost + 1;
      grid->at(rowIndex)->push_back(new Point(toId(colIndex, rowIndex), cost));
    }
  }
}

inline void recomputeGrid() {
  for (auto row = 0u; row < maxRow; ++row) {
    for (auto col = 0u; col < maxCol; ++col) {
      auto current = grid->at(row)->at(col);
      auto updated = new Point(toId(col, row), current->cost);
      delete current;
      grid->at(row)->at(col) = updated;
    }
  }
}

inline void grow() {
  enlargeXaxis();
  enlargYAxis();

  recomputeGrid();
}

bool sortPathsByCost(std::tuple<int, std::vector<Point *> *> left, std::tuple<int, std::vector<Point *> *> right) {
  auto leftCost = std::get<0>(left);
  auto rightCost = std::get<0>(right);
  return leftCost < rightCost;
}

int main() {
  std::ifstream infile("../resources/input.txt");
  std::string line;
  auto rowIndex = 0;
  while (std::getline(infile, line)) {
    if (!line.empty()) {
      addRow(line, rowIndex++);
    }
  }

  grow();
  setNeighbors();

  const auto start = grid->at(0)->at(0);
  const auto end = grid->at(grid->size() - 1)->at(grid->size() - 1);

  navigate(*start, *end, 0);

  std::sort(paths->begin(), paths->end(), sortPathsByCost);
  auto cheapestCost = std::get<0>(paths->at(0));
  std::cout << "Cheapest path cost " << cheapestCost - start->cost << std::endl;
  return 0;
}
