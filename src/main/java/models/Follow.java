package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
            name = JpaConst.Q_FOL_GET_ALL_FOLLOW_MINE,
            query = JpaConst.Q_FOL_GET_ALL_FOLLOW_MINE_DEF)
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
    @Column(name = JpaConst.FOL_COL_FOLLOWEE_ID, nullable = false)
    private Employee followee;

    /**
     * フォローされている従業員id
     */
    @ManyToOne
    @Column(name = JpaConst.FOL_COL_FOLLOWER_ID, nullable = false)
    private Employee follower;

    /**
     * フォローを行った日時
     */
    @Column(name = JpaConst.FOL_COL_FOLLOWED_AT, nullable = false)
    private LocalDateTime followedAt;
}
