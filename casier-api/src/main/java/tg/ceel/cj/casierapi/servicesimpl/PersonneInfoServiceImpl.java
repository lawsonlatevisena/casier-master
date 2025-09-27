package tg.ceel.cj.casierapi.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.PersonneInfoDto;
import tg.ceel.cj.casierapi.entities.PersonneInfo;
import tg.ceel.cj.casierapi.mappers.EntityMapper;
import tg.ceel.cj.casierapi.repositories.PersonneInfoRepository;
import tg.ceel.cj.casierapi.repositories.UserRepository;
import tg.ceel.cj.casierapi.services.LogService;
import tg.ceel.cj.casierapi.services.PersonneInfoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonneInfoServiceImpl implements PersonneInfoService {
    Logger logger = LoggerFactory.getLogger(PersonneInfoServiceImpl.class);
    private final PersonneInfoRepository personneInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EntityMapper entityMapper;

    private final UserRepository userRepository;
    private final LogService logService;

    public PersonneInfoServiceImpl(PersonneInfoRepository personneInfoRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EntityMapper entityMapper, UserRepository userRepository, LogService logService) {
        this.personneInfoRepository = personneInfoRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.entityMapper = entityMapper;
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @Override
    public List<PersonneInfoDto> findAll() {
        List<PersonneInfo> list = personneInfoRepository.findAll();
        return list.stream().map(s -> entityMapper.personneInfoTopersonneInfoDto(s)).collect(Collectors.toList());
    }

    @Override
    public PersonneInfoDto save(PersonneInfoDto dto) {
        return null;
    }

    @Override
    public PersonneInfoDto findById(Long id) {
        return null;
    }

    @Override
    public PersonneInfoDto findByUsername(String username) {
        return null;
    }

    @Override
    public PersonneInfoDto update(PersonneInfoDto dto, String code) {
        return null;
    }

    @Override
    public PersonneInfoDto update(Long id, PersonneInfoDto PersonneInfoDto) {
        return null;
    }


}
