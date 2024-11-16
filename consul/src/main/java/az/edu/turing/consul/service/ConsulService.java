package az.edu.turing.consul.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsulService {

    @Value("${config.ms-identity.key}")
    private String keyValue;

    public String getKeyValue() {
        return keyValue;
    }
}
