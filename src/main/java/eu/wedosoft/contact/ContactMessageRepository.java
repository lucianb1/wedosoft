package eu.wedosoft.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the "message" table.
 *
 */
@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {
}
