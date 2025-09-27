package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tg.ceel.cj.casierapi.entities.Payement;
import tg.ceel.cj.casierapi.models.PaymentStatByMonthModel;

import java.util.List;

public interface PayementRepository extends JpaRepository<Payement, Long> {
    @Query(value =" WITH months AS (\n" +
            "  SELECT generate_series AS month,\n" +
            "  CASE generate_series\n" +
            "    WHEN 1 THEN 'Janvier'\n" +
            "    WHEN 2 THEN 'Février'\n" +
            "    WHEN 3 THEN 'Mars'\n" +
            "    WHEN 4 THEN 'Avril'\n" +
            "    WHEN 5 THEN 'Mai'\n" +
            "    WHEN 6 THEN 'Juin'\n" +
            "    WHEN 7 THEN 'Juillet'\n" +
            "    WHEN 8 THEN 'Août'\n" +
            "    WHEN 9 THEN 'Septembre'\n" +
            "    WHEN 10 THEN 'Octobre'\n" +
            "    WHEN 11 THEN 'Novembre'\n" +
            "    WHEN 12 THEN 'Décembre'\n" +
            "  END AS month_name\n" +
            "  FROM generate_series(1,12)\n" +
            "),\n" +
            "totals_flooz AS (\n" +
            "  SELECT \n" +
            "    EXTRACT(MONTH FROM p.datepayement) as mon,\n" +
            "   sum(d.nombre_copie) * 500 as total\n" +
            "  FROM \n" +
            "    demandes d, \n" +
            "    payements p  \n" +
            "  WHERE \n" +
            "    p.id = d.id_payement AND \n" +
            "   EXTRACT(year  FROM p.datepayement) = ?1 AND\n" +
            "    p.regler AND \n" +
            "    p.canal_payement=0  \n" +
            "  GROUP BY \n" +
            "    EXTRACT(MONTH FROM p.datepayement)\n" +
            "),\n" +
            "totals_t_money AS (\n" +
            "  SELECT \n" +
            "    EXTRACT(MONTH FROM p.datepayement) as mon,\n" +
            "    sum(d.nombre_copie) * 500 as total\n" +
            "  FROM \n" +
            "    demandes d, \n" +
            "    payements p  \n" +
            "  WHERE \n" +
            "    p.id = d.id_payement AND \n" +
            "   EXTRACT(year  FROM p.datepayement) = ?1 AND\n" +
            "    p.regler AND \n" +
            "    p.canal_payement=1  \n" +
            "  GROUP BY \n" +
            "    EXTRACT(MONTH FROM p.datepayement)\n" +
            ")\n" +
            "SELECT \n" +
            "  months.month_name as mois,\n" +
            "  COALESCE(totals_flooz.total, 0) as flooz,COALESCE(totals_t_money.total, 0) as tmoney\n" +
            "FROM months\n" +
            "LEFT JOIN \n" +
            "  totals_flooz ON months.month = totals_flooz.mon\n" +
            "LEFT JOIN \n" +
            "  totals_t_money ON months.month = totals_t_money.mon\n" +
            "order by month ",nativeQuery = true)
    List<PaymentStatByMonthModel> getStatistiqueByMonth(Integer year);
}