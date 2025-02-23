package xyz.slosa.commons;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Handler<T> {

    Map<String, T> map();

    default Stream<T> search(String substring) {
        return this.map().entrySet().stream()
                .filter(entry -> entry.getKey().contains(substring))
                .map(Map.Entry::getValue);
    }

    default Optional<T> match(String element) {
        return Optional.ofNullable(this.map().get(element));
    }

    default Stream<T> with(Predicate<T> test) {
        return this.all().stream()
                .filter(test);
    }

    default Collection<T> all() {
        return this.map().values();
    }

    default void add(String element, T object) {
        this.map().putIfAbsent(element, object);
    }

    default boolean remove(String element) {
        return this.map().remove(element) != null;
    }

}