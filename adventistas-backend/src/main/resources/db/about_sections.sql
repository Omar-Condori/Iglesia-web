-- ==========================================
-- TABLA: about_sections
-- Descripción: Secciones de "Sobre Nosotros"
-- ==========================================

CREATE TABLE IF NOT EXISTS about_sections (
    id BIGSERIAL PRIMARY KEY,
    slug VARCHAR(100) UNIQUE NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    icon VARCHAR(50),
    sort_order INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- DATOS INICIALES
-- ==========================================

INSERT INTO about_sections (slug, title, content, icon, sort_order, is_active) VALUES
(
    'quienes-somos',
    'Quiénes Somos',
    '<h2>Identidad Adventista</h2><p>Somos una comunidad cristiana adventista que busca crecer en fe, servir al prójimo y prepararnos para el pronto regreso de Jesús.</p><h3>Nuestra Comunidad</h3><p>Somos una familia diversa unida por la fe en Cristo, comprometida con:</p><ul><li>La salud integral</li><li>La educación basada en valores</li><li>El estudio profundo de la Biblia</li><li>La misión evangelizadora</li></ul>',
    'users-cog',
    1,
    true
),
(
    'mision',
    'Nuestra Misión',
    '<h2>Propósito de la Iglesia</h2><p><strong>Guiar a todas las personas a una relación más profunda con Cristo</strong> mediante:</p><ul><li>La adoración significativa</li><li>El estudio sistemático de la Biblia</li><li>La oración constante</li><li>El servicio desinteresado</li></ul><p>Buscamos ser instrumentos de transformación en nuestra comunidad, compartiendo el amor de Dios y las buenas nuevas del evangelio.</p>',
    'crosshairs',
    2,
    true
),
(
    'vision',
    'Nuestra Visión',
    '<h2>Qué Esperamos Alcanzar</h2><p><strong>Ser una iglesia que transforma vidas</strong>, impulsa la misión en la comunidad y refleja el carácter de Jesús en todo lo que hacemos.</p><h3>Metas Específicas:</h3><ul><li>Formar discípulos comprometidos con Cristo</li><li>Fortalecer familias con principios bíblicos</li><li>Servir activamente a la comunidad</li><li>Preparar generaciones para el retorno de Cristo</li></ul>',
    'eye',
    3,
    true
),
(
    'creencias',
    'En Qué Creemos',
    '<h2>Creencias Fundamentales Adventistas</h2><h3>1. La Biblia</h3><p>La Palabra de Dios, nuestra única regla de fe y práctica.</p><h3>2. La Segunda Venida de Cristo</h3><p>Creemos en el pronto regreso visible y glorioso de Jesús.</p><h3>3. El Sábado</h3><p>El séptimo día como día de reposo y adoración según el mandamiento.</p><h3>4. Salud y Estilo de Vida</h3><p>Cuidar nuestro cuerpo como templo del Espíritu Santo.</p><h3>5. Salvación en Cristo</h3><p>La salvación es un regalo gratuito de Dios por medio de la fe en Jesús.</p><h3>6. Espíritu de Profecía</h3><p>Reconocemos el ministerio profético dado a la iglesia.</p><p><a href="https://www.adventistas.org/es/creencias/" target="_blank">Ver las 28 Creencias Fundamentales →</a></p>',
    'book-bible',
    4,
    true
),
(
    'historia',
    'Historia de la Iglesia',
    '<h2>Nuestros Orígenes</h2><h3>Fundación</h3><p><strong>Año:</strong> [Completar con año de fundación]</p><p><strong>Cómo Comenzó:</strong> [Historia del inicio de la iglesia local]</p><h3>Crecimiento</h3><p>A lo largo de los años, nuestra congregación ha crecido...</p><ul><li>Primeros años: [Descripción]</li><li>Expansión: [Descripción]</li><li>Actualidad: [Descripción]</li></ul><h3>Pastores que Han Dirigido</h3><ul><li>Pastor [Nombre] - [Años]</li><li>Pastor [Nombre] - [Años]</li></ul><h3>Proyectos Importantes</h3><ul><li>[Proyecto 1]</li><li>[Proyecto 2]</li><li>[Proyecto 3]</li></ul>',
    'history',
    5,
    true
)
ON CONFLICT (slug) DO NOTHING;

-- ==========================================
-- VERIFICAR DATOS
-- ==========================================
SELECT * FROM about_sections ORDER BY sort_order;
