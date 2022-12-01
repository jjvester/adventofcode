#include <algorithm>
#include <cctype>
#include <fstream>
#include <functional>
#include <iostream>
#include <locale>
#include <map>
#include <sstream>
#include <string>
#include <tuple>
#include <vector>

auto *intersections = new std::map<std::string, int>();

std::string ltrim(std::string &src) {
  src.erase(src.begin(),
            std::find_if(src.begin(), src.end(),
                         std::not1(std::ptr_fun<int, int>(std::isspace))));
  return src;
}

std::string rtrim(std::string &src) {
  src.erase(std::find_if(src.rbegin(), src.rend(),
                         std::not1(std::ptr_fun<int, int>(std::isspace)))
                .base(),
            src.end());
  return src;
}

struct Point {
  Point(const int &x, const int &y) : x(x), y(y) {}
  const int x;
  const int y;
};

struct Line {
  Line(const Point &from, const Point &to) : from(from), to(to) {}
  const Point from;
  const Point to;
};

Point *makePoint(std::string &src) {
  std::stringstream ss(src);
  std::string token;

  std::getline(ss, token, ',');
  auto rightXTrimmed = rtrim(token);
  auto x = ltrim(rightXTrimmed);

  std::getline(ss, token, ',');
  auto rightYTrimmed = rtrim(token);
  auto y = ltrim(rightYTrimmed);

  return new Point(stoi(x), stoi(y));
}

Line *split(const std::string &str) {
  std::stringstream ss(str);
  std::string token;

  std::getline(ss, token, ' ');
  Point *from = makePoint(token);

  std::getline(ss, token, ' ');

  std::getline(ss, token, ' ');
  Point *to = makePoint(token);

  return new Line(*from, *to);
}

bool isVertical(const Line &line) {
  auto from = line.from;
  auto to = line.to;

  return from.x == to.x && from.y != to.y;
}

bool isHorizontal(const Line &line) {
  auto from = line.from;
  auto to = line.to;

  return from.y == to.y && from.x != to.x;
}

bool isALine(const Line &line) {
  auto from = line.from;
  auto to = line.to;

  return from.x - to.x != 0 || from.y - to.y != 0;
}

bool isValid(const Line &line) {
  auto from = line.from;
  auto to = line.to;

  return (isHorizontal(line) || isVertical(line)) && isALine(line);
}

bool exists(const std::string &id) {
  std::map<std::string, int>::iterator it = intersections->find(id);
  if (it != intersections->end()) {
    return true;
  }

  return false;
}

const std::string toId(const Point &point) {
  return std::to_string(point.x) + std::to_string(point.y);
}

void drawVertical(const Point &point, const Point &destination) {
  if (point.y <= destination.y) {
    auto id = toId(point);
    if (exists(id)) {
      const auto updated = intersections->at(id) + 1;
      intersections->erase(id);
      intersections->insert(std::make_pair(id, updated));
    } else {
      intersections->insert(std::make_pair(id, 1));
    }

    const auto next = new Point(point.x, point.y + 1);
    drawVertical(*next, destination);
  }
}

void drawVertical(const Line &line) {
  auto from = line.from;
  auto to = line.to;

  if (from.y < to.y) {
    drawVertical(from, to);
  } else {
    drawVertical(to, from);
  }
}

void drawHorizontal(const Point &point, const Point &destination) {
  if (point.x <= destination.x) {
    auto id = toId(point);
    if (exists(id)) {
      const auto updated = intersections->at(id) + 1;
      intersections->erase(id);
      intersections->insert(std::make_pair(id, updated));
    } else {
      intersections->insert(std::make_pair(id, 1));
    }

    const auto next = new Point(point.x + 1, point.y);
    drawHorizontal(*next, destination);
  }
}

void drawHorizontal(const Line &line) {
  auto from = line.from;
  auto to = line.to;

  if (from.x < to.x) {
    drawHorizontal(from, to);
  } else {
    drawHorizontal(to, from);
  }
}

int main() {
  std::string binaryInput;
  std::ifstream infile("../resources/input");
  std::string record;

  int records = 0;
  while (std::getline(infile, record)) {
    if (!record.empty()) {
      auto line = split(record);
      records++;
      std::cout << line->from.x << ":" << line->from.y << " to " << line->to.x
                << ":" << line->to.y << std::endl;

      if (isValid(*line)) {

        if (isVertical(*line)) {
          drawVertical(*line);
        } else {
          drawHorizontal(*line);
        }
      }
    }
  }

  std::cout << records << std::endl;

  int total = 0;
  std::map<std::string, int>::iterator it = intersections->begin();
  while (it != intersections->end()) {
    auto id = it->first;
    auto count = it->second;
    if (count >= 2) {
      total += 1;
    }

    std::cout << id << ":" << count << std::endl;
    it++;
  }

  std::cout << "Total is " << total << std::endl;

  return 0;
}
