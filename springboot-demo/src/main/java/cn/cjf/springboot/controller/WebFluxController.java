package cn.cjf.springboot.controller;

import cn.cjf.springboot.bean.JpaPerson;
import cn.cjf.springboot.dao.PersonRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebFluxController {
    private final PersonRepository repository;

    public WebFluxController(PersonRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/person")
    Mono<Void> create(@RequestBody Publisher<JpaPerson> personStream) {
        return this.repository.save(personStream).then();
    }

    @GetMapping("/person")
    Flux<JpaPerson> list() {
        return this.repository.findAll();
    }

    @GetMapping("/person/{id}")
    Mono<JpaPerson> findById(@PathVariable Integer id) {
        return this.repository.findOne(id);
    }
}