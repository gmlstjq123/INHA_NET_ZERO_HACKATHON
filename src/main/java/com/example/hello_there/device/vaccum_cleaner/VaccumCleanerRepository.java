package com.example.hello_there.device.vaccum_cleaner;

import com.example.hello_there.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccumCleanerRepository extends JpaRepository<Device, Long> {
}
