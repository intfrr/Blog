package mx.com.robertoleon.seguridad

class Usuario {

    transient springSecurityService

    String username
    String password
    Integer reputacion = 5
    boolean votante = false
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String nombre
    String apellidoPaterno
    String apellidoMaterno
    String email
    Date fechaAlta
    Archivo imagen


    static constraints = {
        username blank: false, unique: true
        password blank: false
        nombre nullable: true
        apellidoPaterno nullable: true
        apellidoMaterno nullable: true
        email email: true, nullable: false
        imagen nullable: true
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Rol> getAuthorities() {
        UsuarioRol.findAllByUsuario(this).collect { it.rol } as Set
    }

    def beforeInsert() {
        enabled = true
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }

    String toString(){
        username
    }

}
