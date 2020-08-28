package ru.mango.vpbx.repository.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.mango.common.model.vpbx.Call;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.repository.CallRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CallRepositoryRedis implements CallRepository {

    private HashOperations hashOps;
    private ListOperations listOps;

    public CallRepositoryRedis(RedisTemplate redisTemplate) {
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
    }

    @Override
    public Optional<VpbxCallDto> getCall(String vpbxApiKey, String entryId) {
        return Optional.ofNullable((VpbxCallDto) hashOps.get(vpbxApiKey, entryId));
    }

    @Override
    public List<VpbxCallDto> getCallEvents(String entryId) {
        List result = this.listOps.range(entryId, 0, listOps.size(entryId) - 1);
        if (result != null) {
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<VpbxCallDto> getCalls(String vpbxApiKey) {
        return hashOps.values(vpbxApiKey);
    }

    @Override
    public void delete(String vpbxApiKey) {
        List<VpbxCallDto> calls = getCalls(vpbxApiKey);
        for (VpbxCallDto currentCall : calls) {
         hashOps.delete(vpbxApiKey,currentCall.getEntryId());
        }
    }
    @Override
    public void saveCall(String vpbxApiKey, String entryId, VpbxCallDto call) {
        hashOps.put(vpbxApiKey, entryId, call);
        this.listOps.rightPush(call.getEntryId(), call);
    }

    @Override
    public void deleteCall(String vpbxApiKey, String entryId) {
        hashOps.delete(vpbxApiKey, entryId);
    }

}
