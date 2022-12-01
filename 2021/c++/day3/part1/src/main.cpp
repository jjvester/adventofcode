#include <vector>
#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <tuple>

const auto ZERO = '0';
const auto ONE = '1';
const auto MAX_BINARY_NUMBER_LENGTH = 12;
const auto COL_INIT_VALUES = std::make_tuple(0, 0);


int main() {

	std::vector<std::tuple<int, int>> colCounts(MAX_BINARY_NUMBER_LENGTH);
	fill(colCounts.begin(), colCounts.end(), COL_INIT_VALUES);
	
	std::string binaryInput;	
	std::ifstream infile("input");
	std::string line;

        while (std::getline(infile, line)) {
		std::istringstream iss(line);
        	iss >> binaryInput;        	

		int index = 0;        	
        	for (char elem : binaryInput) {
        		auto current = colCounts.at(index);
        		auto updated = elem == ZERO ? std::make_tuple(std::get<0>(current) + 1, std::get<1>(current)) : std::make_tuple(std::get<0>(current), std::get<1>(current) + 1);        		
     			colCounts.at(index) = updated;   
     			index ++;     		
        	}
        }
        
        for (int i = 0; i < colCounts.size(); ++i) {
        	auto item = colCounts.at(i);
        	std::cout << "Col " << i << " [" << std::get<0>(item) << "|" << std::get<1>(item) << "]" << std::endl;
        }
        
        std::cout << std::endl;
        
        std::string gamma;
        std::string epsilon;
        
        for (int i = 0; i < colCounts.size(); ++i) {
              	auto item = colCounts.at(i);
        	int zero = std::get<0>(item);
        	int one = std::get<1>(item);
        	
        	gamma.push_back(zero > one ? '0' : '1');
        	epsilon.push_back(zero > one ? '1' : '0');
        }
        
        std::cout << "Gamma   " << gamma << std::endl;
        std::cout << "Epsilon " << epsilon << std::endl;
        
        auto decimalGamma = stol(gamma, 0, 2);
        auto decimalEpsilon = stol(epsilon, 0, 2);
        auto powerConsumption = decimalGamma * decimalEpsilon;
        
        std::cout << "Decimal Gamma   " << stol(gamma, 0, 2) << std::endl;
        std::cout << "Decimal Epsilon " << stol(epsilon, 0, 2) << std::endl;
        
        std::cout << "Power consumption " << powerConsumption << std::endl;

}
