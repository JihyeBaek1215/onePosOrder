package onepos.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer>{

    Order findById(int id);
    Order findByStoreIdAndId(int storeId, int id);
}
