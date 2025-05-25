package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.template;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.security.JwtTokenProvider;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.LowonganService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public abstract class DosenLowonganAction<T> {

    protected abstract T doAction(Lowongan lowongan);

    public T execute(Long lowonganId,
                     HttpServletRequest request,
                     LowonganService lowonganService,
                     JwtTokenProvider jwtTokenProvider) {

        Claims claims = jwtTokenProvider.getAllClaimsFromToken(request.getHeader("Authorization").substring(7));
        Long userId = claims.get("userId", Integer.class).longValue();

        Lowongan lowongan = lowonganService.findById(lowonganId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lowongan not found"));

        if (!lowongan.getCreatedBy().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the creator.");
        }

        return doAction(lowongan);
    }
}
