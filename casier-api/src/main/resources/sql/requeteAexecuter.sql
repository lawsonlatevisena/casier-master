ALTER TABLE public.payements ALTER COLUMN datepayement TYPE timestamp USING datepayement::timestamp;
ALTER TABLE public.demandes ALTER COLUMN id_profession DROP NOT NULL;

update demandes set signee =true where disponible =true and date_disponibilite is not null;
update demandes set invalidee =true where disponible is null and etape_1_motif is not null;
update demandes set valider =true where date_validation is not null;
update demandes set traitee=true where disponible is true;

update  demandes  set disponible =true where invalidee =true and couleur is null and etape_1_motif is not null;


-- mise à jour du champ invalidee pour les demande en attent de signature
update  demandes  set invalidee =false where valider = true and disponible =true;

-- mise à jour du champ signee pour les ancienne demande signe et retiré
update demandes set signee  = true, invalidee =false where retirer =true and valider =true and disponible =true;
-- mise à jour du champ invalidee pour les demande rejetées
update  demandes  set invalidee =true where disponible =false and valider =false and etape_1_motif is not null and etape_1_valider =false ;
-- mise à jour du champ motif_invalidation
update demandes set motif_invalidation =etape_1_motif  ;


--- Type demande ----
INSERT INTO public.types_demandes
(id, libelle, "version", code, created_by, created_date)
VALUES(5, 'Demande B3 PM', 1, 'M3', NULL, NULL);
INSERT INTO public.types_demandes
(id, libelle, "version", code, created_by, created_date)
VALUES(6, 'Demande B2 PM', 1, 'M2', NULL, NULL);
INSERT INTO public.types_demandes
(id, libelle, "version", code, created_by, created_date)
VALUES(7, 'Demande B1 PM', 1, 'M1', NULL, NULL);


