package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.dto.FonctionDto;
import tg.ceel.cj.casierapi.dto.ServiceDemandeurB2Dto;
import tg.ceel.cj.casierapi.entities.ServiceDemandeurB2;

import java.util.List;

public interface ServiceDemandeurB2Service {
    List<ServiceDemandeurB2Dto> getAll();

    ServiceDemandeurB2Dto save(ServiceDemandeurB2Dto serviceDemandeurB2Dto);

    ServiceDemandeurB2Dto update(Integer id, ServiceDemandeurB2Dto serviceDemandeurB2Dto);
}
