package com.zach.demopolls.controllers;

import com.zach.demopolls.Poll;
import com.zach.demopolls.exceptions.ResourceNotFoundException;
import com.zach.demopolls.repos.PollRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.util.Optional;

@RestController
public class PollController {


    @Inject
    private PollRepository pollRepository;

    //View All polls
    @RequestMapping(value="/polls", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Poll>> getAllPolls()
    {        Iterable<Poll> allPolls = pollRepository.findAll();
    return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK); }

    //verify Poll
    protected  void  verifypoll(Long pollId) throws ResourceNotFoundException
    {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(poll == null){
            throw new ResourceNotFoundException("Poll with id"+pollId+" not found");

        }
    }




    //Create Polls
    @RequestMapping(value = "/polls",method = RequestMethod.POST)
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll)
    {
        poll = pollRepository.save(poll);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();

        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    //View One Poll
    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        Optional<Poll> p = pollRepository.findById(pollId);
        if(p == null){
            throw new ResourceNotFoundException("Poll with id" +pollId +"not found");
        }
    return new ResponseEntity<> (p, HttpStatus.OK);
    }

    //Update
    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId)
    {
        verifypoll(pollId);
        // Save the entity
                Poll p = pollRepository.save(poll);
                return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete
    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId)
    {
        verifypoll(pollId);
        pollRepository.deleteById(pollId);
    return new ResponseEntity<>(HttpStatus.OK); }

}
