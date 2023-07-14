package com.example.hello_there.userDevice;

import com.example.hello_there.device.Device;
import com.example.hello_there.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDevice {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDeviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;
}
