package gr.hua.it21774.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import gr.hua.it21774.dto.BasicCourseDTO;
import gr.hua.it21774.respository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<BasicCourseDTO> searchCourses(String name, List<String> selectedCoures) {

        return courseRepository.searchCourses(translate(name.toLowerCase()), name, selectedCoures);
    }

    private String translate(String name) {
        Map<Character, Character> map = new HashMap<>();
        map.put('a', 'α');
        map.put('b', 'β');
        map.put('c', 'κ');
        map.put('d', 'δ');
        map.put('e', 'ε');
        map.put('f', 'φ');
        map.put('g', 'γ');
        map.put('h', 'η');
        map.put('i', 'ι');
        map.put('j', 'ξ');
        map.put('k', 'κ');
        map.put('l', 'λ');
        map.put('m', 'μ');
        map.put('n', 'ν');
        map.put('o', 'ο');
        map.put('p', 'π');
        map.put('q', 'α');
        map.put('r', 'ρ');
        map.put('s', 'σ');
        map.put('t', 'τ');
        map.put('u', 'υ');
        map.put('v', 'ν');
        map.put('w', 'ω');
        map.put('x', 'χ');
        map.put('y', 'υ');
        map.put('z', 'ζ');

        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            result.append(map.getOrDefault(c, c));
        }
        return result.toString();

    }
}
