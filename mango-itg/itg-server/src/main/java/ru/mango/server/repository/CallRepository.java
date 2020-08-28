package ru.mango.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mango.common.model.vpbx.Call;

import java.util.List;

public interface CallRepository extends MongoRepository<Call, String> {

    List<Call> findByExt(String ext);

}
