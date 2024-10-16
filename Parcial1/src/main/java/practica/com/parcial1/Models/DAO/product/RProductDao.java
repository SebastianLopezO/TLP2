package practica.com.parcial1.Models.DAO.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import practica.com.parcial1.Models.Entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RProductDao implements IProductDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return em.createQuery("from Product").getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAnother(Long id) {
        List<Product> products = findAll();
        return products.stream()
                .filter(product -> !product.getId().equals(id))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void Save(Product product) {
        if (product.getId() != null && product.getId() > 0) {
            try {
                Product existingProduct = em.find(Product.class, product.getId());
                if (existingProduct != null) {
                    product.setDetails(existingProduct.getDetails());
                    em.merge(product);
                }
            } catch (Exception e) {
                throw new PersistenceException(e);
            }
        } else {
            try {
                em.persist(product);
            } catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    @Transactional
    @Override
    public void Delete(Long id) {
        Product product = findOne(id);
        em.remove(product);
    }

    @Transactional
    @Override
    public void Drop() {
        List<Product> products = findAll();
        for (Product product : products) {
            em.remove(product);
        }
    }

}
