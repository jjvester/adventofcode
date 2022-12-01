#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <map>

bool exists(int key, std::map<int, long> *dict)
{
    return dict->find(key) != dict->end();
}

int main()
{

    auto counts = new std::map<int, long>();
    counts->insert(std::make_pair(0, 0));
    counts->insert(std::make_pair(1, 0));
    counts->insert(std::make_pair(2, 0));
    counts->insert(std::make_pair(3, 0));
    counts->insert(std::make_pair(4, 0));
    counts->insert(std::make_pair(5, 0));
    counts->insert(std::make_pair(6, 0));
    counts->insert(std::make_pair(7, 0));
    counts->insert(std::make_pair(8, 0));

    

    std::string input;
    std::ifstream infile("input");
    std::string line;

    while (std::getline(infile, line))
    {
        std::istringstream iss(line);
        std::string token;
        while (std::getline(iss, token, ','))
        {
            std::cout << token << std::endl;
            int key = std::stoi(token);
            if (exists(key, counts))
            {
                int current = counts->at(key);
                (*counts)[key] = current + 1;
            }
            else
            {
                counts->insert(std::make_pair(key, 1));
            }
        }
    }

    for (unsigned int i = 0; i < 256; ++i)
    {
        long prev = counts->at(8);
        long current = 0;
        for (int j = 7; j >= 0; --j) {
            current = counts->at(j);
            (*counts)[j] = prev;
            prev = current;

            if (j == 0) {
                std::cout << "Current zero is " << current << " Moving these to 6 and making new 8's" << std::endl;
                long recurringParents = current + counts->at(6);                
                (*counts)[6] = recurringParents;
                (*counts)[8] = current;
            }
        }

    }

    long total = 0;
    std::map<int, long>::iterator it = counts->begin();
    while (it != counts->end())
    {
        std::cout << it->first << ":" << it->second << std::endl;
        total += it->second;
        it++;
    }

    std::cout << "Total fish "<< total << std::endl;
    return 0;
}