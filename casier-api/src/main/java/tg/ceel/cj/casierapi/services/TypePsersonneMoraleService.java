package tg.ceel.cj.casierapi.services;

import tg.ceel.cj.casierapi.ints.ITypePersonneMorale;

import java.util.List;

public interface TypePsersonneMoraleService {
    void initTypePersonne();
    List<ITypePersonneMorale> getTypePersonneMorale();
}
