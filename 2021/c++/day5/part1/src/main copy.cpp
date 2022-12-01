#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <tuple>
#include <vector>

int maxX = 0;
int maxY = 0;

struct Point {
  Point(const int &x, const int &y) : x(x), y(y), intersections(0) {}
  Point(const int &x, const int &y, const int &intersections)
      : x(x), y(y), intersections(intersections) {}
  const int x;
  const int y;
  int intersections;
};

struct Line {
  Line(Point *from, Point *to) : from(from), to(to) {}
  Point *from;
  Point *to;
};

struct Grid {
  Grid(std::vector<Line *> *lines) : lines(lines) {}
  std::vector<Line *> *lines;
  std::map<std::string, Point *> *intersections =
      new std::map<std::string, Point *>();

  void draw() {
    for (unsigned int i = 0; i < lines->size(); ++i) {
      auto *line = lines->at(i);
      if (isValid(*line)) {
        auto *from = line->from;
        auto *to = line->to;

        if (isVertical(*line)) {
          //   auto fromId = std::to_string(from->x) + std::to_string(from->y);
          //   auto toId = std::to_string(to->x) + std::to_string(to->y);

          //   std::cout << "Vertical line from id " << fromId << " to " << toId
          //             << std::endl;P

          drawVertical(line);
        } else {
          drawHorizontal(line);
        }
      }
    }
  }

  void drawVertical(Line *line) {
    auto *from = line->from;
    auto *to = line->to;

    if (from->y < to->y) {
      drawVertical(from, to);
    } else {
      drawVertical(to, from);
    }
  }

  void drawHorizontal(Line *line) {
    auto *from = line->from;
    auto *to = line->to;

    if (from->x < to->x) {
      drawHorizontal(from, to);
    } else {
      drawHorizontal(to, from);
    }
  }

  const std::string toId(const Point *point) const {
    return std::to_string(point->x) + std::to_string(point->y);
  }

  void drawHorizontal(Point *point, Point *destination) {
    if (point->x <= destination->x) {
      auto id = toId(point);
      Point *updated;
      if (exists(id)) {
        auto match = intersections->find(id);
        auto current = match->second;
        updated = new Point(current->x, current->y, current->intersections + 1);
      } else {
        updated = new Point(point->x, point->y, 1);
      }

      intersections->erase(id);
      intersections->insert(std::make_pair(id, updated));
      drawHorizontal(new Point(point->x + 1, point->y), destination);
    }
  }

  void drawVertical(Point *point, Point *destination) {
    if (point->y <= destination->y) {
      auto id = toId(point);
      Point *updated;
      if (exists(id)) {
        auto match = intersections->find(id);
        auto current = match->second;
        updated = new Point(current->x, current->y, current->intersections + 1);
      } else {
        updated = new Point(point->x, point->y, 1);
      }

      intersections->erase(id);
      intersections->insert(std::make_pair(id, updated));
      drawVertical(new Point(point->x, point->y + 1), destination);
    }
  }

  bool exists(const std::string &id) {
    std::map<std::string, Point *>::iterator it = intersections->find(id);
    if (it != intersections->end()) {
      return true;
    }

    return false;
  }

  bool isValid(Line &line) {
    auto *from = line.from;
    auto *to = line.to;

    return isHorizontal(line) || isVertical(line);
  }

  bool isVertical(Line &line) {
    auto *from = line.from;
    auto *to = line.to;

    return from->x == to->x && from->y != to->y;
  }

  bool isHorizontal(Line &line) {
    auto *from = line.from;
    auto *to = line.to;

    return from->y == to->y && from->x != to->x;
  }
};

Point *makePoint(std::string &src) {
  std::stringstream ss(src);
  std::string token;
  std::string x;
  std::string y;

  unsigned int index = 0;
  while (std::getline(ss, token, ',')) {
    if (index++ == 0) {
      x = token;
    } else {
      y = token;
    }
  }

  return new Point(stoi(x), stoi(y));
}

Line *split(std::string str) {
  Line *line;
  Point *from;
  Point *to;
  std::stringstream ss(str);
  std::string token;

  int index = 0;
  while (std::getline(ss, token, ' ')) {
    if (!token.empty() && token.compare("->") != 0) {
      // std::cout << token << std::endl;
      if (index++ == 0) {
        from = makePoint(token);
      } else {
        to = makePoint(token);
      }
    }
  }

  return new Line(from, to);
}

void updateGridDimensions(Line &line) {
  maxX = std::max(line.from->x, maxX);
  maxY = std::max(line.from->y, maxY);

  maxX = std::max(line.to->x, maxX);
  maxY = std::max(line.to->y, maxY);
}

inline std::ostream &operator<<(std::ostream &out, const Grid &grid) {
  auto *intersections = grid.intersections;
  int safeZones = 0;
  std::map<std::string, Point *>::iterator it = intersections->begin();
  while (it != intersections->end()) {
    auto id = it->first;
    auto *point = it->second;
    if (point->intersections >= 2) {
      safeZones++;
    }

    // out << id << ": " << point->intersections << std::endl;
    it++;
  }

  for (unsigned int i = 0; i <= maxY; ++i) {
    for (unsigned int j = 0; j <= maxX; ++j) {
      const auto point = new Point(j, i);
      const auto id = grid.toId(point);
      std::map<std::string, Point *>::iterator it =
          grid.intersections->find(id);
      if (it != intersections->end()) {
        out << it->second->intersections << ".";
      } else {
        out << "..";
      }
    }
    out << std::endl;
  }

  out << "Safe zones " << safeZones << std::endl;
  return out;
}

int main() {
  std::string binaryInput;
  std::ifstream infile("../resources/input");
  std::string record;

  auto *lines = new std::vector<Line *>();
  while (std::getline(infile, record)) {
    if (!record.empty()) {
      auto line = split(record);

      updateGridDimensions(*line);
      lines->push_back(line);
    }
  }

  auto *grid = new Grid(lines);
  grid->draw();

  std::cout << *grid << std::endl;
  std::cout << "Number of lines " << lines->size() << std::endl;
  std::cout << "Number of intersections " << grid->intersections->size()
            << std::endl;
  return 0;
}
