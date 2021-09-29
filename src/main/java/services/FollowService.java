package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FollowConverter;
import actions.views.FollowView;
import constants.JpaConst;
import models.Follow;
import models.validators.FollowValidator;

/**
 * フォローテーブルの操作に関わる処理を行うクラス
 */
public class FollowService extends ServiceBase {

    /**
     * 指定した従業員がフォロー中の従業員データを、指定されたページ数の一覧画面に表示する分取得しFollowViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータリスト
     */
    public List<FollowView> getMineFolloweePerPage(EmployeeView employee, int page){

        List<Follow> followee = em.createNamedQuery(JpaConst.Q_FOL_GET_ALL_FOLLOWEE_MINE, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowConverter.toViewList(followee);
    }

    /**
     * 指定した従業員のフォロワーの従業員データを、指定されたページ数の一覧画面に表示する分取得しFollowViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータリスト
     */
    public List<FollowView> getMineFollowerPerPage(EmployeeView employee, int page){

        List<Follow> follower = em.createNamedQuery(JpaConst.Q_FOL_GET_ALL_FOLLOWER_MINE, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowConverter.toViewList(follower);
    }

    /**
     * 指定した従業員のフォローしている従業員数を取得し、返却する
     * @return フォローの件数
     */
    public long countMineFollow(EmployeeView employee) {
        long followee_count = (long)em.createNamedQuery(JpaConst.Q_FOL_COUNT_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return followee_count;
    }

    /**
     * 設定されたフォローデータを元に、フォローテーブルに1件フォロー情報を登録する
     * @param fv
     * @return
     */
    public List<String> create(FollowView fv){
        List<String> errors = FollowValidator.validate(fv);
        if(errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            fv.setFollowedAt(ldt);
            createFollow(fv);
        }
        //バリデーションで発生したエラーを返却
        return errors;
    }

    private void createFollow(FollowView fv) {

        em.getTransaction().begin();
        em.persist(FollowConverter.toModel(fv));
        em.getTransaction().commit();
    }

}
