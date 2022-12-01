use tailcall::tailcall;

pub fn part1(input: Vec<String>) -> i32 {
    #[tailcall]
    fn recur(src: &Vec<String>, idx: usize, mut curr: i32, mut max: i32) -> i32 {
        if end_of_input(&src, idx) {
            return max;
        }

        let item = src.get(idx).unwrap();
        return if item.trim().is_empty() {
            max = if max < curr || max == 0 { curr } else { max };
            recur(src, idx + 1, 0, max)
        } else {
            recur(src, idx + 1, curr + item.parse::<i32>().unwrap(), max)
        };
    }

    return recur(&input, 0, 0, 0);
}

pub fn part2(input: Vec<String>) -> Vec<i32> {
    #[tailcall]
    fn recur(src: &Vec<String>, idx: usize, curr: i32, mut max: Vec<i32>) -> Vec<i32> {
        if end_of_input(&src, idx) {
            max.sort();
            if max.get(0).unwrap().clone() < curr {
                max[0] = curr;
            }

            return max;
        }

        let item = src.get(idx).unwrap();
        return if item.trim().is_empty() {
            max.sort();
            if max.len() < 3 {
                max.push(curr);
            } else if max.get(0).unwrap().clone() < curr {
                max[0] = curr;
            }

            recur(src, idx + 1, 0, max)
        } else {
            recur(src, idx + 1, curr + item.parse::<i32>().unwrap(), max)
        };
    }

    return recur(&input, 0, 0, vec![]);
}

fn end_of_input(src: &Vec<String>, idx: usize) -> bool {
    return idx >= src.len();
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn sample1() {
        let input: Vec<String> = vec![String::from("1000"), String::from(""), String::from("2000"), String::from("3000"), String::from(""), String::from("4000"), String::from(""), String::from("5000"), String::from("6000"), String::from(""),
                                      String::from("7000"), String::from("8000"), String::from("9000"), String::from(""), String::from("10000")];
        assert_eq!(part1(input), 24000);
    }

    #[test]
    fn sample2() {
        let input: Vec<String> = vec![String::from("1000"), String::from(""), String::from("2000"), String::from("3000"), String::from(""), String::from("4000"), String::from(""), String::from("5000"), String::from("6000"), String::from(""),
                                      String::from("7000"), String::from("8000"), String::from("9000"), String::from(""), String::from("10000")];
        let result: i32 = part2(input).iter().sum();
        assert_eq!(result, 45000);
    }
}
