#include <algorithm>
#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

struct Cell {
  Cell(const std::string &value, bool occupied)
      : value(value), occupied(occupied) {}
  const std::string value;
  bool occupied;
};

struct Board {
  explicit Board(std::vector<std::vector<std::string> *> *board) {
    rows = new std::vector<std::vector<Cell *> *>();
    for (unsigned int i = 0; i < 5; ++i) {
      auto *row = new std::vector<Cell *>();
      for (unsigned int j = 0; j < 5; ++j) {
        auto *cell = new Cell(board->at(i)->at(j), false);
        row->push_back(cell);
      }

      rows->push_back(row);
    }
  }

  inline void occupy(const std::string &number) {
    for (unsigned int i = 0; i < 5; ++i) {
      auto *row = rows->at(i);
      for (unsigned int j = 0; j < 5; ++j) {
        auto *cell = row->at(j);
        if (number.compare(cell->value) == 0) {
          auto *updated = new Cell(cell->value, true);
          row->at(j) = updated;
        }
      }
    }
  }

  inline bool isWinner() {
    for (unsigned int i = 0; i < 5; ++i) {
      auto *row = rows->at(i);
      if (isRowOccupied(*row)) {
        return true;
      }
    }

    return isColOccupied(*rows->at(0));
  }

  inline bool isColOccupied(const int &col, const int &row) {
    auto *cell = rows->at(row)->at(col);
    if (row == 5) {
      return true;
    } else if (!cell->occupied) {
      return false;
    }

    return isColOccupied(col, row + 1);
  }

  inline bool isColOccupied(const std::vector<Cell *> &row) {
    for (unsigned int i = 0; i < 5; ++i) {
      if (isColOccupied(i, 0)) {
        return true;
      }
    }

    return false;
  }

  inline bool isRowOccupied(const std::vector<Cell *> &row) {
    for (auto *cell : row) {
      if (!cell->occupied) {
        return false;
      }
    }

    return true;
  }

  std::vector<std::vector<Cell *> *> *rows;
};

std::vector<std::string> *split(std::string str, char delimiter) {
  auto *result = new std::vector<std::string>();
  std::stringstream ss(str);
  std::string token;

  while (std::getline(ss, token, delimiter)) {
    if (!token.empty()) {
      result->push_back(token);
    }
  }

  return result;
}

inline std::ostream &operator<<(std::ostream &out, const Board &board) {
  for (unsigned int i = 0; i < 5; ++i) {
    auto *row = board.rows->at(i);
    for (unsigned int j = 0; j < 5; ++j) {
      auto *cell = row->at(j);
      out << cell->value << " " << (!cell->occupied ? "false " : "true ");
    }

    out << std::endl;
  }

  return out;
}

struct Bingo {
  Bingo(const std::vector<Board *> &boards,
        const std::vector<std::string> &numbers)
      : boards(boards), numbers(numbers) {}

  const std::vector<Board *> boards;
  const std::vector<std::string> numbers;

  void play() {
    for (auto number : numbers) {
      if (occupy(number)) {
        exit(0);
      }
    }
  }

  inline bool occupy(const std::string &number) {
    for (auto *board : boards) {
      board->occupy(number);

      if (board->isWinner()) {
        std::cout << "We have a winner" << std::endl;
        std::cout << *board << std::endl;

        int sum = sumUnmarked(*board);
        long total = sum * stoi(number);
        std::cout << "Bingo is " << total << std::endl;
        return true;
      }
    }

    return false;
  }

  inline int sumUnmarked(const Board &board) {
    int sum = 0;
    for (unsigned int i = 0; i < 5; ++i) {
      auto *row = board.rows->at(i);
      for (unsigned int j = 0; j < 5; ++j) {
        auto *cell = row->at(j);
        sum += !cell->occupied ? stoi(cell->value) : 0;
      }
    }

    return sum;
  }
};

int main() {

  std::string binaryInput;
  std::ifstream infile("../resources/input");
  std::string line;

  auto *numbers = new std::vector<std::string>();
  auto *rows = new std::vector<std::vector<std::string> *>();
  auto *boards = new std::vector<Board *>();

  unsigned int index = 0;
  unsigned int rowIndex = 0;
  while (std::getline(infile, line)) {
    if (index == 0) {
      numbers = split(line, ',');
      index++;
    } else {
      if (!line.empty()) {
        if (rowIndex == 5) {
          boards->push_back(new Board(rows));
          rows = new std::vector<std::vector<std::string> *>();
          rowIndex = 0;
        }
        auto *row = split(line, ' ');
        rows->push_back(row);
        rowIndex++;
      }
    }
  }

  if (rows->size() == 5) {
    boards->push_back(new Board(rows));
  }

  Bingo bingo = Bingo(*boards, *numbers);
  bingo.play();
}
