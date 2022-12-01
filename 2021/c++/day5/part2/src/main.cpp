#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>

struct Point {
  Point(const int x, const int y) : x(x), y(y) {}
  const int x;
  const int y;

  const bool operator==(const Point &other) const {
    return x == other.x && y == other.y;
  }

  const bool operator<(const Point &other) const {
    return x < other.x || (x == other.x && y < other.y);
  }

  // const bool operator>(const Point &other) const {
  //   return x > other.x || (x == other.x && y > other.y);
  // }
};

struct Line {
  Line(const Point &from, const Point &to) : from(from), to(to) {}
  const Point from;
  const Point to;
};

auto intersections = new std::map<Point, unsigned int>();

bool exists(const Point &key) {
  return intersections->find(key) != intersections->end();
}

void add(const Point &point) {
  if (exists(point)) {
    const auto current = intersections->at(point);
    (*intersections)[point] = current + 1;
  } else {
    intersections->insert(std::make_pair(point, 1));
  }
}

inline Point *splitPoint(const std::string &point) {
  std::stringstream ss(point);
  std::string token;

  std::getline(ss, token, ',');
  auto x = token;

  std::getline(ss, token, ',');
  auto y = token;

  return new Point(stoi(x), stoi(y));
}

inline Line *splitLine(const std::string &line) {
  std::stringstream ss(line);
  std::string token;

  std::getline(ss, token, ' ');
  Point *from = splitPoint(token);

  std::getline(ss, token, ' ');

  std::getline(ss, token, ' ');
  Point *to = splitPoint(token);

  return new Line(*from, *to);
};

inline std::ostream &operator<<(std::ostream &out, const Line &line) {
  out << line.from.x << ":" << line.from.y << "->" << line.to.x << ":"
      << line.to.y << std::endl;
  return out;
}

inline bool isDiagonal(const Line &line) {
  const auto from = line.from;
  const auto to = line.to;

  bool xIsIncreasing = to.x - from.x >= 0;
  bool yIsIncreasing = to.y - from.y >= 0;

  int x = from.x;
  int y = from.y;

  while (y != to.y) {
    y = yIsIncreasing ? y + 1 : y - 1;
    x = xIsIncreasing ? x + 1 : x - 1;
  }

  return x == to.x;
}

void addAll(std::vector<Point *> *points) {
  for (auto point : (*points)) {
    add(*point);
  }
}

inline void drawDiagonal(const Line &line) {
  auto points = new std::vector<Point *>();

  const auto from = line.from;
  const auto to = line.to;

  bool xIsIncreasing = to.x - from.x >= 0;
  bool yIsIncreasing = to.y - from.y >= 0;

  int x = from.x;
  int y = from.y;

  while (y != to.y) {
    points->push_back(new Point(x, y));

    y = yIsIncreasing ? y + 1 : y - 1;
    x = xIsIncreasing ? x + 1 : x - 1;
  }

  points->push_back(new Point(x, y));

  std::cout << "" << std::endl;

  // points->push_back(
  //     new Point(xIsIncreasing ? x + 1 : x - 1, yIsIncreasing ? y + 1 : y -
  //     1));

  addAll(points);
}

inline bool isVertical(const Line &line) {
  const auto from = line.from;
  const auto to = line.to;

  return from.x == to.x;
}

inline bool isHorizontal(const Line &line) {
  const auto from = line.from;
  const auto to = line.to;

  return from.y == to.y;
}

inline bool isValid(const Line &line) {
  return (isHorizontal(line) || isVertical(line) || isDiagonal(line));
}

inline void drawVertical(const Line &line) {
  const auto start = std::min(line.from.y, line.to.y);
  const auto end = std::max(line.from.y, line.to.y);

  for (int i = start; i <= end; ++i) {
    auto next = new Point(line.from.x, i);
    add(*next);
  }
}

inline void drawHorizontal(const Line &line) {
  const auto start = std::min(line.from.x, line.to.x);
  const auto end = std::max(line.from.x, line.to.x);

  for (int i = start; i <= end; ++i) {
    auto next = new Point(i, line.from.y);
    add(*next);
  }
}

inline void draw(const Line &line) {
  if (isValid(line)) {
    if (isVertical(line)) {
      drawVertical(line);
    } else if (isHorizontal(line)) {
      drawHorizontal(line);
    } else {
      drawDiagonal(line);
    }

    // std::cout << line << std::endl;
  }
}

int main() {
  std::string input;
  std::ifstream infile("../resources/input");
  std::string record;

  while (std::getline(infile, record)) {
    if (!record.empty()) {
      const auto line = splitLine(record);
      draw(*line);
    }
  }

  int total = 0;
  std::map<Point, unsigned int>::iterator it = intersections->begin();
  while (it != intersections->end()) {
    auto count = it->second;
    if (count >= 2) {
      total += 1;
    }

    it++;
  }

  for (int i = 0; i < 10; ++i) {
    for (int j = 0; j < 10; ++j) {
      const auto key = Point(j, i);
      const auto value = intersections->find(key)->first;
      const auto overlap = intersections->find(key)->second;
      if (overlap > 0) {
        std::cout << overlap << "\t";
      } else {
        std::cout << "."
                  << "\t";
      }
    }
    std::cout << std::endl;
  }

  std::cout << "Total is " << total << std::endl;
  return 0;
}
