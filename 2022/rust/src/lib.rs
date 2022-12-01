mod day1;

use std::fs::File;
use std::io::BufRead;
use std::io::BufReader;

fn read_input_from_path(path: &str) -> Vec<String> {
    let file = file_from_path(path);
    return file_into_strings(file);
}

fn file_from_path(path: &str) -> File {
    return File::open(path).unwrap();
}

fn file_into_strings(file: File) -> Vec<String> {
    let mut strings = Vec::<String>::new();
    let mut reader = BufReader::new(file);
    return read_lines(reader, strings);
}

fn read_lines(mut reader: BufReader<File>, mut strings: Vec<String>) -> Vec<String> {
    let mut buf = String::new();
    let mut n: usize;

    loop {
        n = reader.read_line(&mut buf).unwrap();
        if end_of_file(n) {
            break;
        }
        strings.push(String::from(buf.clone().trim()));
        buf.clear();
    }

    strings
}

fn end_of_file(token: usize) -> bool {
    token == 0
}

#[cfg(test)]
mod tests {
    use super::*;
    use day1::part1;
    use day1::part2;

    #[test]
    fn day1_part1() {
        assert_eq!(part1(read_input_from_path("resources/day1/input")), 69912);
    }

    #[test]
    fn day1_part2() {
        let result: i32 = part2(read_input_from_path("resources/day1/input")).iter().sum();
        assert_eq!(result, 208180);
    }
}
