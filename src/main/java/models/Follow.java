package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * フォローデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_FOL)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_FOL_GET_ALL_My_FOLLOWEE,
            query = JpaConst.Q_FOL_GET_ALL_My_FOLLOWEE_DEF),
    @NamedQuery(
            name = JpaConst.Q_FOL_My_FOLLOWEE_COUNT,
            query = JpaConst.Q_FOL_My_FOLLOWEE_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_FOL_FOLLOEE_CHECK,
            query = JpaConst.Q_FOL_FOLLOEE_CHECK_DEF),
    @NamedQuery(
            name = JpaConst.Q_FOL_GET_ONE_FOLLOWEE,
            query = JpaConst.Q_FOL_GET_ONE_FOLLOWEE_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follow {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.FOL_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * フォローしている従業員id
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FOL_COL_FOLLOWEE_ID, nullable = false)
    private Employee followee;

    /**
     * フォローされている従業員id
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FOL_COL_FOLLOWER_ID, nullable = false)
    private Employee follower;

    /**
     * フォローを行った日時
     */
    @Column(name = JpaConst.FOL_COL_FOLLOWED_AT, nullable = false)
    private LocalDateTime followedAt;
}
