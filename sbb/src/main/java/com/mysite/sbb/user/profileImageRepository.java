package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface profileImageRepository extends JpaRepository<profileImage, Long> {
	
	profileImage findBySiteUser(SiteUser siteUser);

}
