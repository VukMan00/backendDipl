package rs.ac.bg.fon.pracenjepolaganja.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberUserDetails implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password = null;
        List<GrantedAuthority> authorities = null;
        List<Member> member = memberRepository.findByUsername(username);
        if (member.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the member : " + username);
        } else{
            System.out.println(member);
            userName = member.get(0).getUsername();
            password = member.get(0).getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(member.get(0).getRole()));
        }
        return new User(userName,password,authorities);
    }
}
