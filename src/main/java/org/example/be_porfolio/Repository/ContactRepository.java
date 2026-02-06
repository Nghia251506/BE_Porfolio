package org.example.be_porfolio.Repository;

import org.example.be_porfolio.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
