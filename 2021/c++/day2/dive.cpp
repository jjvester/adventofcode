#include <iostream>
#include <sstream>
#include <string>
#include <fstream>

const auto FORWARD = "forward";
const auto DOWN = "down";
const auto UP = "up";

int main() {

	std::string command;
	int value = 0;
	int horizontal = 0;
	int depth = 0;
	
	std::ifstream infile("input");
	std::string line;
        while (std::getline(infile, line)) {
		std::istringstream iss(line);
        	iss >> command >> value;
		std::cout << "Navigation is " << command << " and depth is " << value << std::endl;

		if (FORWARD == command) {
			horizontal += value;
		} else if (DOWN == command) {
			depth += value;
		} else {
			depth -= value;
		}

		value = 0;
	}
	
	std::cout << "Depth is " << depth << " and horizontal is " << horizontal << std::endl;
	std::cout << "Product is " << depth * horizontal << std::endl;
	return 0;
}
