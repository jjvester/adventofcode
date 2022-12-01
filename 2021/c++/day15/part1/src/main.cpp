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

unsigned int rowLength = 0;

auto grid = new std::vector<std::vector<Point *> *>();
auto visited = new std::set<int>();
auto paths = new std::vector<std::tuple<int, std::vector<Point *> *>>();

inline bool exists(const int &key, std::set<int> &visited) {
  return visited.find(key) != visited.end();
}

inline unsigned int toId(const int &x, const int &y) {
  return y == 0 ? x : (y * rowLength) + x;
}

bool isNorth(const Point &point) { return point.id < rowLength; }

bool isSouth(const Point &point) {
  return point.id < grid->size() * rowLength &&
         point.id > ((grid->size() * rowLength) - rowLength);
}

bool isWest(const Point &point) { return point.id % rowLength == 0; }

bool isEast(const Point &point) {
  return point.id % rowLength == (rowLength - 1);
}

bool isNorthWest(const Point &point) { return point.id == 0; }

bool isNorthEast(const Point &point) { return point.id == (rowLength - 1); }

bool isSouthWest(const Point &point) {
  return point.id == (grid->size() * rowLength) - rowLength;
}

bool isSouthEast(const Point &point) {
  return point.id == (grid->size() * rowLength) - 1;
}

inline void setNeighbors() {
  for (auto row : *grid) {
    for (auto point : *row) {
      point->neighbors = new std::vector<Point *>();

      if (isNorthWest(*point)) {
        point->neighbors->push_back(grid->at(0)->at(1));
        point->neighbors->push_back(grid->at(1)->at(0));
      } else if (isNorthEast(*point)) {
        point->neighbors->push_back(grid->at(0)->at(rowLength - 2));
        point->neighbors->push_back(grid->at(1)->at(rowLength - 1));
      } else if (isSouthWest(*point)) {
        point->neighbors->push_back(grid->at(grid->size() - 1)->at(1));
        point->neighbors->push_back(grid->at(grid->size() - 2)->at(0));
      } else if (isSouthEast(*point)) {
        point->neighbors->push_back(
            grid->at(grid->size() - 1)->at(rowLength - 2));
        point->neighbors->push_back(
            grid->at(grid->size() - 2)->at(rowLength - 1));
      } else if (isNorth(*point)) {
        point->neighbors->push_back(grid->at(0)->at(point->id - 1));
        point->neighbors->push_back(grid->at(0)->at(point->id + 1));
        point->neighbors->push_back(grid->at(1)->at(point->id % rowLength));
      } else if (isSouth(*point)) {
        point->neighbors->push_back(
            grid->at(grid->size() - 1)->at((point->id % rowLength) - 1));
        point->neighbors->push_back(
            grid->at(grid->size() - 1)->at((point->id % rowLength) + 1));
        point->neighbors->push_back(
            grid->at(grid->size() - 2)->at(point->id % rowLength));
      } else if (isWest(*point)) {
        point->neighbors->push_back(grid->at(point->id / rowLength - 1)->at(0));
        point->neighbors->push_back(grid->at(point->id / rowLength + 1)->at(0));
        point->neighbors->push_back(grid->at(point->id / rowLength)->at(1));
      } else if (isEast(*point)) {
        point->neighbors->push_back(
            grid->at(point->id / rowLength - 1)->at(rowLength - 1));
        point->neighbors->push_back(
            grid->at(point->id / rowLength + 1)->at(rowLength - 1));
        point->neighbors->push_back(
            grid->at(point->id / rowLength)->at(rowLength - 2));
      } else {
        point->neighbors->push_back(
            grid->at(point->id / rowLength)->at((point->id % rowLength) - 1));
        point->neighbors->push_back(
            grid->at(point->id / rowLength)->at((point->id % rowLength) + 1));
        point->neighbors->push_back(
            grid->at(point->id / rowLength - 1)->at(point->id % rowLength));
        point->neighbors->push_back(
            grid->at(point->id / rowLength + 1)->at(point->id % rowLength));
      }
    }
  }
}

bool sortByTravelCost(Point *left, Point *right) {
  return left->travelCost < right->travelCost;
}

void relaxNeighbors(Point *current) {
  for (auto neighbor : *current->neighbors) {
    neighbor->travelCost =
        std::min(current->travelCost + neighbor->cost, neighbor->travelCost);
  }
}

void sortNeighbors(Point *current) {
  std::sort(current->neighbors->begin(), current->neighbors->end(),
            sortByTravelCost);
}

bool isVisited(const Point &point, std::vector<Point *> *path) {
  for (auto elem : *path) {
    if (elem->id == point.id) {
      return true;
    }
  }
  return false;
}

bool isNeighborWorthy(const Point &origin, const Point &neighbor,
                      std::vector<Point *> *path) {
  return origin.travelCost + neighbor.cost < neighbor.travelCost &&
         !isVisited(neighbor, path);
}

void relaxNeighbor(const Point &origin, Point &neighbor) {
  neighbor.travelCost = origin.travelCost + neighbor.cost;
}

void next(Point *source, Point *current, std::stack<Point *> *candidates) {
  /* for (auto neighbor : *current->neighbors) {
   if (isNeighborWorthy(*source, *neighbor)) {
        relaxNeighbor(*source, *neighbor);
        candidates->push(neighbor);

      std::cout << "Current: " << current->id << " with travel cost "
        << current->travelCost << " -> Neigbor : " << neighbor->id
        << " cost: " << neighbor->cost
        << " travel cost: " << neighbor->travelCost << std::endl;
         }

   }*/
}

void navigate(Point &source, Point &destination, int cost) {
  auto stack = new std::stack<Point *>();

  source.travelCost = 0;
  stack->push(&source);

  while (!stack->empty() && stack->top()->id != destination.id) {
    auto current = stack->top();
    stack->pop();

    next(&source, current, stack);
  }

  auto end = stack->top();
  stack->pop();
  std::cout << end->id << " " << end->travelCost;
  std::cout << std::endl;
}

std::vector<Point *> *copy(std::vector<Point *> *src) {
  auto result = new std::vector<Point *>();
  for (auto elem : *src) {
    result->push_back(elem);
  }
  return result;
}

bool costAcceptable(const int &costSoFar) {
  return !currentTotalCostSet || costSoFar < currentTotalCost;
}

void next2(Point *current, Point *destination, std::vector<Point *> *path,
           int costSoFar) {
  if (current->id != destination->id && costAcceptable(costSoFar)) {
    for (auto neighbor : *current->neighbors) {
      if (isNeighborWorthy(*current, *neighbor, path)) {
        relaxNeighbor(*current, *neighbor);
        auto breadcrumbs = copy(path);
        breadcrumbs->push_back(neighbor);
        next2(neighbor, destination, breadcrumbs, current->cost + costSoFar);
      }
    }
  } else if (current->id == destination->id) {
    // std::cout << "Found a path:" << std::endl;
    int cost = 0;
    for (auto point : *path) {
      // std::cout << point->id << " ";
      cost += point->cost;
    }
    // std::cout << std::endl << "Cost " << cost << std::endl;
    paths->push_back(std::make_tuple(cost, path));
    if (!currentTotalCostSet) {
      currentTotalCostSet = true;
      currentTotalCost = cost;
    } else {
      currentTotalCost = std::min(currentTotalCost, cost);
    }
  }
}

void navigate2(Point &source, Point &destination, int cost) {
  auto path = new std::vector<Point *>();
  source.travelCost = cost;
  path->push_back(&source);
  next2(&source, &destination, path, cost);
}

inline void addRow(const std::string &src, const int &rowIndex) {
  auto row = new std::vector<Point *>();
  for (auto i = 0; i < src.length(); ++i) {
    row->push_back(new Point(toId(i, rowIndex), src[i] - 48));
  }

  rowLength = row->size();
  grid->push_back(row);
}

bool sortPathsByCost(std::tuple<int, std::vector<Point *> *> left,
                     std::tuple<int, std::vector<Point *> *> right) {
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

  setNeighbors();

  const auto start = grid->at(0)->at(0);
  const auto end = grid->at(grid->size() - 1)->at(grid->size() - 1);

  navigate2(*start, *end, 0);

  std::sort(paths->begin(), paths->end(), sortPathsByCost);
  auto cheapestCost = std::get<0>(paths->at(0));
  std::cout << "Cheapest path cost " << cheapestCost - start->cost << std::endl;
  // for (auto tuple : *paths) {
  //   std::cout << "Path cost " << std::get<0>(tuple) - start->cost <<
  //   std::endl;
  // }

  return 0;
}
