#include <iostream>
#include <sstream>
#include <string>
#include <fstream>

int main() {
	std::ifstream infile("input");
	std::string line;

	bool started = false;
	int prev = 0;
	int count = 0;

	while (std::getline(infile, line)) {
	    std::istringstream iss(line);
	    int depth;
            iss >> depth;

	    if (started && depth > prev) {
		count ++;
	    }

	    prev = depth;
	    std::cout << depth << std::endl;
	    started = true;
	}

	std::cout << "Number of times depth has increased is " << count << std::endl;	
	return 0;
}
