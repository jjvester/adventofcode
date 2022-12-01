#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <set>
#include <sstream>
#include <string>
#include <vector>

const auto HASH = "#";
const auto DOT = ".";
const auto FOLD_ALONG_Y = "fold along y=";
const auto FOLD_ALONG_X = "fold along x=";

auto maxX = 0u;
auto maxY = 0u;

struct Fold {
  Fold(const int &line, const bool &isX) : line(line), isX(isX) {}
  const unsigned int line;
  const bool isX;
};

struct Point {
  Point(const unsigned int &x, const unsigned int &y, const std::string &label)
      : x(x), y(y), label(label) {}
  const unsigned int x;
  const unsigned int y;
  const std::string label;
};

const unsigned int toId(const unsigned int &x, const unsigned int &y,
                        const unsigned int &maxX) {
  return y == 0 ? x : (y * maxX) + x;
}

const bool isXFold(const std::string &src) {
  return src.find(FOLD_ALONG_X, 0) != std::string::npos;
}

const bool isYFold(const std::string &src) {
  return src.find(FOLD_ALONG_Y, 0) != std::string::npos;
}

const bool isFold(const std::string &src) {
  return isXFold(src) || isYFold(src);
}

const Fold *splitFold(const std::string &src) {
  std::stringstream ss(src);
  std::string token;
  std::getline(ss, token, '=');
  std::getline(ss, token, '=');

  return isXFold(src) ? new Fold(std::stoi(token), true)
                      : new Fold(std::stoi(token), false);
}

Point *splitPoint(const std::string &src) {
  std::stringstream ss(src);
  std::string token;

  std::getline(ss, token, ',');
  unsigned int x = std::stoi(token);
  maxX = std::max(maxX, x);

  std::getline(ss, token, ',');
  unsigned int y = std::stoi(token);
  maxY = std::max(maxY, y);

  return new Point(x, y, HASH);
}

const bool exists(const unsigned int &key,
                  const std::map<const unsigned int, Point *> &points) {
  return points.find(key) != points.end();
}

void draw(const std::map<const unsigned int, Point *> &points) {
  for (unsigned int y = 0; y < maxY; ++y) {
    for (unsigned int x = 0; x < maxX; ++x) {
      auto empty = Point(x, y, DOT);
      auto id = toId(empty.x, empty.y, maxX);

      if (exists(id, points)) {
        auto point = points.find(id)->second;
        std::cout << point->label; // << id << " ";
        continue;
      }

      std::cout << empty.label; // << id << " ";
    }

    std::cout << std::endl;
  }
}

void copy(const std::vector<Point *> &src,
          std::map<const unsigned int, Point *> &points) {
  for (auto point : src) {
    auto id = toId(point->x, point->y, maxX);
    points.insert(std::make_pair(id, point));
  }
}

std::map<const unsigned int, Point *> *
doVerticalFold(const unsigned int &line,
               std::map<const unsigned int, Point *> &points) {

  auto result = new std::map<const unsigned int, Point *>();
  for (unsigned int y = 0; y < maxY; ++y) {
    auto left = line - 1;
    auto right = line + 1;

    for (unsigned int x = right; x < maxX; ++x) {
      auto id = toId(left, y, maxX);
      auto foldedId = toId(x, y, maxX);

      if (exists(foldedId, points) || exists(id, points)) {
        result->insert(
            std::make_pair(toId(left, y, line), new Point(left, y, HASH)));
      }

      left--;
    }
  }

  maxX = line;
  return result;
}

std::map<const unsigned int, Point *> *
doHorziontalFold(const unsigned int &line,
                 std::map<const unsigned int, Point *> &points) {

  auto rowId = line - 1;
  auto result = new std::map<const unsigned int, Point *>();
  for (unsigned int y = line + 1; y < maxY; ++y) {
    for (unsigned int x = 0; x < maxX; ++x) {
      auto id = toId(x, rowId, maxX);
      auto foldedId = toId(x, y, maxX);

      if (exists(foldedId, points) || exists(id, points)) {
        result->insert(std::make_pair(id, new Point(x, rowId, HASH)));
      }
    }

    rowId--;
  }

  maxY = line;
  return result;
}

std::map<const unsigned int, Point *> *
doFold(const Fold &fold, std::map<const unsigned int, Point *> &points) {
  auto line = fold.line;
  std::cout << "Doing fold " << (fold.isX ? " vertically" : " horizontally")
            << " at line " << line << std::endl;

  return fold.isX ? doVerticalFold(line, points)
                  : doHorziontalFold(line, points);
}

int main() {
  auto folds = std::vector<const Fold *>();
  auto tmp = std::vector<Point *>();
  auto points = new std::map<const unsigned int, Point *>();

  std::ifstream infile("../resources/input");
  std::string line;
  while (std::getline(infile, line)) {
    if (!line.empty()) {
      if (isFold(line)) {
        folds.push_back(splitFold(line));
        continue;
      }

      tmp.push_back(splitPoint(line));
    }
  }

  maxX += 1;
  maxY += 1;

  copy(tmp, *points);

  for (auto fold : folds) {
    auto prev = points;
    points = doFold(*fold, *prev);
    delete prev;
    draw(*points);

    std::cout << "Total dots after fold " << points->size() << std::endl;
  }

  return 0;
}