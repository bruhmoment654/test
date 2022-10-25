package com.example.test.service;


import com.example.test.model.Operation;
import com.example.test.utils.OperationUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OperationService {

    private OperationUtils solver;

    @Cacheable(cacheNames = "exec", key = "#operation.getOperation_name() + #operation.getFile_path()")
    public Object execute(Operation operation) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Method declaredMethod : getClass().getDeclaredMethods()) {
            if (declaredMethod.getName().equals(operation.getOperation_name())) {
                return declaredMethod.invoke(this, openFile(operation.getFile_path()));
            }
        }
        return null;
    }

    public File openFile(String file_path) throws FileNotFoundException {
        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + file_path);
    }

    public Long get_max_value(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.getPath()))
                .stream()
                .mapToLong(Long::parseLong)
                .max()
                .getAsLong();

    /*     Scanner scanner = new Scanner(file);

               long max = Long.MIN_VALUE;
               while (scanner.hasNext()) {
                   long value = scanner.nextLong();
                   max = Math.max(value, max);
               }
               return max;
    */
    }

    public Long get_min_value(File file) throws IOException {
        System.out.println(file.hashCode());
        return Files.readAllLines(Paths.get(file.getPath()))
                .stream().mapToLong(Long::parseLong)
                .min()
                .getAsLong();
    }

    public Long get_median(File file) throws IOException {
        List<Long> list = Files.readAllLines(Paths.get(file.getPath()))
                .stream().mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        return solver.findMedian(list, list.size());
    }

    public Double get_average_value(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.getPath()))
                .stream()
                .mapToLong(Long::parseLong)
                .average()
                .getAsDouble();
    }

    public List<List<Long>> get_longest_subsequence_inc(File file) throws IOException {
        List<Long> list = Files.readAllLines(Paths.get(file.getPath()))
                .stream().mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        return solver.getIncSubarrays(list);

    }

    public List<List<Long>> get_longest_subsequence_dec(File file) throws IOException {
        List<Long> list = Files.readAllLines(Paths.get(file.getPath()))
                .stream().mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        return solver.getDecSubarrays(list);

    }

}
