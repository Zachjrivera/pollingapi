package com.zach.demopolls.repos;

import com.zach.demopolls.domains.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll,Long>{

}


