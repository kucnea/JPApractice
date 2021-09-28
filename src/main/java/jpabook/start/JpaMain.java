package jpabook.start;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {


            tx.begin(); //트랜잭션 시작
            logic(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

        String id = "id1";
        JpaMember jpaMember = new JpaMember();
        jpaMember.setId(id);
        jpaMember.setUsername("지한");
        jpaMember.setAge(2);

        //등록
        em.persist(jpaMember);

        //수정
        jpaMember.setAge(20);

        //한 건 조회
        JpaMember findJpaMember = em.find(JpaMember.class, id);
        System.out.println("findJpaMember=" + findJpaMember.getUsername() + ", age=" + findJpaMember.getAge());

        //목록 조회
        List<JpaMember> jpaMembers = em.createQuery("select m from JpaMember m", JpaMember.class).getResultList();
        System.out.println("jpaMembers.size=" + jpaMembers.size());

        //삭제
        em.remove(jpaMember);

    }
}
