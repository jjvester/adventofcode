#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <vector>

int main() {
	std::ifstream infile("input");
	std::string line;
	std::vector<std::vector<int>> windows;

	bool started = false;
	int prev = 0;
	int index = 0;
	std::vector<int> items;
	int count = 0;

	while (std::getline(infile, line)) {
	    std::istringstream iss(line);
	    int depth;
            iss >> depth;

	    if (index <= 2) {
	        items.push_back(depth);
	    }
	    
	    if (index == 2) {
      //	        std::cout << "Items are " << " " << items.at(0) << " " << items.at(1) << " " << items.at(2) << std::endl;
		prev = items.at(0) + items.at(1) + items.at(2);
		started = true;
	    } else if (index > 2) {
		    std::cout << "Items were " << items.at(0) << " " << items.at(1) << " " << items.at(2) << std::endl;
		    items.at(0) = items.at(1);
		    items.at(1) = items.at(2);
		    items.at(2) = depth;

		    int total = items.at(0) + items.at(1) + items.at(2);
		    std::cout << "Items are  " << items.at(0) << " " << items.at(1) << " " << items.at(2) << std::endl;
		    std::cout << "Comparing  " << prev << " with " << total << std::endl << std::endl;
		    count += total > prev ? 1 : 0;
		    prev = total;
	    }

	    index ++;
	}

	std::cout << "Depth window increases " << count << std::endl;
	return 0;
}
