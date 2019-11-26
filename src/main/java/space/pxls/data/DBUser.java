package space.pxls.data;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import space.pxls.user.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBUser {
    public int id;
    public int stacked;
    public int chatNameColor;
    public String username;
    public String login;
    public long cooldownExpiry;
    public Role role;
    public long banExpiry;
    public boolean isPermaChatbanned;
    public long chatbanExpiry;
    public boolean isRenameRequested;
    public String discordName;
    public String chatbanReason;

    public DBUser(int id, int stacked, String username, String login, long cooldownExpiry, Role role, long banExpiry, boolean isPermaChatbanned, long chatbanExpiry, boolean isRenameRequested, String discordName, String chatbanReason, int chatNameColor) {
        this.id = id;
        this.stacked = stacked;
        this.username = username;
        this.login = login;
        this.cooldownExpiry = cooldownExpiry;
        this.role = role;
        this.banExpiry = banExpiry;
        this.isPermaChatbanned = isPermaChatbanned;
        this.chatbanExpiry = chatbanExpiry;
        this.isRenameRequested = isRenameRequested;
        this.discordName = discordName;
        this.chatbanReason = chatbanReason;
        this.chatNameColor = chatNameColor;
    }

    public static class Mapper implements RowMapper<DBUser> {
        @Override
        public DBUser map(ResultSet r, StatementContext ctx) throws SQLException {
            Timestamp stamp = r.getTimestamp("cooldown_expiry");
            Timestamp ban = r.getTimestamp("ban_expiry");
            Timestamp chatban = r.getTimestamp("chat_ban_expiry");
            return new DBUser(
                    r.getInt("id"),
                    r.getInt("stacked"),
                    r.getString("username"),
                    r.getString("login"),
                    stamp == null ? 0 : stamp.getTime(),
                    Role.valueOf(r.getString("role")),
                    ban == null ? 0 : ban.getTime(),
                    r.getBoolean("perma_chat_banned"),
                    chatban == null ? 0 : chatban.getTime(),
                    r.getBoolean("is_rename_requested"),
                    r.getString("discord_name"),
                    r.getString("chat_ban_reason"),
                    r.getInt("chat_name_color")
            );
        }
    }
}
