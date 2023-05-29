package rs.ac.bg.fon.pracenjepolaganja.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.AuthorityRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Authority;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MemberUserDetails implements UserDetailsService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password = null;
        List<GrantedAuthority> authorities = null;
        List<Member> member = memberRepository.findByUsername(username);
        if (member.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the member : " + username);
        } else{
            Set<Authority> memberAuthority = authorityRepository.findByMemberId(member.get(0).getId());
            member.get(0).setAuthorities(memberAuthority);
            userName = member.get(0).getUsername();
            password = member.get(0).getPassword();
            authorities = new ArrayList<>();
            for(Authority authority:memberAuthority) {
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
            }
        }
        return new User(userName,password,authorities);
    }
}
